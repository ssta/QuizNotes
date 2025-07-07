# Frontend Architecture

## Overview

The frontend for the Twitch Quiz Game System is built with React and TypeScript, providing a responsive and interactive
user experience for both quizmasters and players. This document outlines the frontend architecture, component structure,
state management, and key design decisions.

## Technology Stack

- **Core Framework**: React 18+ with TypeScript
- **Build Tool**: Vite
- **Styling**: Material-UI (MUI) v5 with custom theming
- **Routing**: React Router v6
- **State Management**: React Context API + React Query
- **Real-time Communication**: SockJS and STOMP.js
- **Form Management**: React Hook Form
- **Testing**: Vitest and React Testing Library
- **Linting/Formatting**: ESLint and Prettier

## Project Structure

```
src/
├── assets/             # Static assets (images, fonts, etc.)
├── components/         # Reusable UI components
│   ├── common/         # Shared components across features
│   ├── quizmaster/     # Quizmaster-specific components
│   └── player/         # Player-specific components
├── config/             # Configuration files
├── contexts/           # React context providers
├── hooks/              # Custom React hooks
├── layouts/            # Page layout components
├── pages/              # Top-level page components
│   ├── auth/           # Authentication pages
│   ├── quizmaster/     # Quizmaster dashboard pages
│   └── player/         # Player game pages
├── services/           # API and service integrations
│   ├── api/            # REST API clients
│   ├── socket/         # WebSocket connection and handlers
│   └── twitch/         # Twitch API integration
├── types/              # TypeScript type definitions
├── utils/              # Utility functions
├── App.tsx             # Root component
├── main.tsx            # Entry point
└── vite-env.d.ts       # Vite type declarations
```

## Component Hierarchy

### Quizmaster Interface

```
App
└── AuthenticatedRoute
    └── QuizmasterLayout
        ├── Sidebar
        │   ├── QuizList
        │   └── Navigation
        └── MainContent
            ├── DashboardPage
            ├── QuizCreationPage
            │   ├── QuizForm
            │   └── QuestionEditor
            │       ├── QuestionList
            │       ├── QuestionForm
            │       └── ImageUploader
            ├── QuizControlPage
            │   ├── PlayerList
            │   ├── QuestionControls
            │   ├── QuizTimer
            │   └── Leaderboard
            └── SettingsPage
```

### Player Interface

```
App
└── PlayerLayout
    ├── JoinQuizPage
    │   └── NicknameForm
    ├── WaitingRoomPage
    │   ├── PlayerList
    │   └── StatusMessage
    ├── QuestionPage
    │   ├── QuestionDisplay
    │   │   ├── QuestionText
    │   │   └── QuestionImage
    │   ├── AnswerOptions
    │   ├── Timer
    │   └── ScoreIndicator
    └── ResultsPage
        ├── PersonalScore
        └── Leaderboard
```

## State Management

### Context Providers

```typescript
// AuthContext.tsx
export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<PropsWithChildren> = ({children}) => {
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);

    // Authentication logic

    return (
        <AuthContext.Provider value = {
    {
        user, login, logout, loading
    }
}>
    {
        children
    }
    </AuthContext.Provider>
)
    ;
};
```

### Data Fetching with React Query

```typescript
// useQuizzes.ts
export const useQuizzes = () => {
    return useQuery<Quiz[], Error>({
        queryKey: ['quizzes'],
        queryFn: () => quizService.fetchQuizzes(),
    });
};

// useQuiz.ts
export const useQuiz = (quizId: string) => {
    return useQuery<Quiz, Error>({
        queryKey: ['quiz', quizId],
        queryFn: () => quizService.fetchQuiz(quizId),
        enabled: !!quizId,
    });
};
```

## Real-time Communication

### WebSocket Connection

```typescript
// socketService.ts
export class SocketService {
    private client: Client | null = null;
    private subscriptions: Map<string, Subscription> = new Map();

    connect(sessionId: string): Promise<void> {
        return new Promise((resolve, reject) => {
            this.client = new Client({
                brokerURL: `${WS_BASE_URL}/ws`,
                connectHeaders: {
                    'X-Session-Id': sessionId,
                },
                debug: function (str) {
                    console.log('STOMP: ' + str);
                },
                reconnectDelay: 5000,
                onConnect: () => resolve(),
                onStompError: (error) => reject(error),
            });

            this.client.activate();
        });
    }

    // Subscription methods

    disconnect(): void {
        if (this.client) {
            this.client.deactivate();
            this.client = null;
            this.subscriptions.clear();
        }
    }
}
```

### React Hook for WebSocket

```typescript
// useQuizSocket.ts
export const useQuizSocket = (quizId: string) => {
    const socketService = useSocketService();
    const [connected, setConnected] = useState(false);
    const [currentQuestion, setCurrentQuestion] = useState<Question | null>(null);
    const [quizState, setQuizState] = useState<QuizState>('WAITING');
    const [scores, setScores] = useState<Score[]>([]);

    useEffect(() => {
        const connect = async () => {
            try {
                await socketService.connect(getSessionId());
                setConnected(true);

                // Subscribe to topics
                socketService.subscribe(`/topic/quiz/${quizId}/state`, (message) => {
                    setQuizState(JSON.parse(message.body));
                });

                socketService.subscribe(`/topic/quiz/${quizId}/question`, (message) => {
                    setCurrentQuestion(JSON.parse(message.body));
                });

                socketService.subscribe(`/topic/quiz/${quizId}/scores`, (message) => {
                    setScores(JSON.parse(message.body));
                });
            } catch (error) {
                console.error('Failed to connect to WebSocket', error);
            }
        };

        if (quizId) {
            connect();
        }

        return () => {
            socketService.disconnect();
            setConnected(false);
        };
    }, [quizId, socketService]);

    const submitAnswer = useCallback((answerId: number) => {
        if (connected && currentQuestion) {
            socketService.send(`/app/quiz/${quizId}/answer`, {
                questionId: currentQuestion.id,
                selectedOption: answerId,
                timestamp: new Date().toISOString(),
            });
        }
    }, [connected, currentQuestion, quizId, socketService]);

    return {
        connected,
        quizState,
        currentQuestion,
        scores,
        submitAnswer,
    };
};
```

## Responsive Design

The frontend is designed with a mobile-first approach, ensuring functionality across devices:

```typescript
// useResponsive.ts
export const useResponsive = () => {
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
    const isTablet = useMediaQuery(theme.breakpoints.between('sm', 'md'));
    const isDesktop = useMediaQuery(theme.breakpoints.up('md'));

    return {isMobile, isTablet, isDesktop};
};
```

## Error Handling

```typescript
// ErrorBoundary.tsx
export class ErrorBoundary extends React.Component<
    PropsWithChildren<{ fallback: React.ReactNode }>,
    { hasError: boolean }
> {
    constructor(props: PropsWithChildren<{ fallback: React.ReactNode }>) {
        super(props);
        this.state = {hasError: false};
    }

    static getDerivedStateFromError(_: Error) {
        return {hasError: true};
    }

    componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
        console.error('Uncaught error:', error, errorInfo);
        // Log to monitoring service
    }

    render() {
        if (this.state.hasError) {
            return this.props.fallback;
        }

        return this.props.children;
    }
}
```

## Performance Optimizations

1. **Code Splitting**:
   ```typescript
   // App.tsx
   const QuizmasterDashboard = lazy(() => import('./pages/quizmaster/Dashboard'));
   const PlayerGame = lazy(() => import('./pages/player/Game'));
   ```

2. **Memoization**:
   ```typescript
   // Leaderboard.tsx
   export const Leaderboard = React.memo(({ scores }: LeaderboardProps) => {
     // Component logic
   });
   ```

3. **Virtualization for long lists**:
   ```typescript
   // PlayerList.tsx
   import { FixedSizeList } from 'react-window';

   export const PlayerList = ({ players }: PlayerListProps) => {
     return (
       <FixedSizeList
         height={400}
         width="100%"
         itemCount={players.length}
         itemSize={50}
       >
         {({ index, style }) => (
           <div style={style}>
             <PlayerItem player={players[index]} />
           </div>
         )}
       </FixedSizeList>
     );
   };
   ```

## Accessibility

The frontend implements accessibility best practices:

1. Semantic HTML elements
2. ARIA attributes where necessary
3. Keyboard navigation support
4. Color contrast compliance
5. Screen reader compatibility

## Testing Strategy

1. **Unit Tests**:
   ```typescript
   // QuestionForm.test.tsx
   describe('QuestionForm', () => {
     it('should submit form with valid data', async () => {
       const onSubmit = vi.fn();
       render(<QuestionForm onSubmit={onSubmit} />);

       // Test form submission
     });
   });
   ```

2. **Integration Tests**:
   ```typescript
   // PlayerGame.test.tsx
   describe('PlayerGame', () => {
     it('should allow answering questions', async () => {
       // Mock socket service
       // Render component
       // Test interaction flow
     });
   });
   ```

## Build and Deployment

- **Development**: Vite dev server with hot module replacement
- **Production Build**: Optimized build with code splitting
- **Deployment**: Static files served from CDN
- **Environment Configuration**: Environment variables for different deployments

```typescript
// vite.config.ts
export default defineConfig(({mode}) => {
    return {
        plugins: [react()],
        build: {
            sourcemap: mode !== 'production',
            rollupOptions: {
                output: {
                    manualChunks: {
                        vendor: ['react', 'react-dom', 'react-router-dom'],
                        mui: ['@mui/material', '@mui/icons-material'],
                    },
                },
            },
        },
        server: {
            proxy: {
                '/api': {
                    target: 'http://localhost:8080',
                    changeOrigin: true,
                },
                '/ws': {
                    target: 'ws://localhost:8080',
                    ws: true,
                },
            },
        },
    };
});
```
