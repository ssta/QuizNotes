# Quiz Frontend

This directory contains the React TypeScript frontend for the Twitch Quiz Game System.

## Getting Started

### Prerequisites

- Node.js 20+
- npm 10+ or yarn 1.22+

### Installation

```bash
npm install
# or
yarn install
```

### Development

```bash
npm run dev
# or
yarn dev
```

This will start the development server at http://localhost:5173

### Building for Production

```bash
npm run build
# or
yarn build
```

## Project Structure

The frontend follows the structure outlined in the technical specification:

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

## Key Features

- **Quizmaster Interface**: Dashboard for creating and managing quizzes
- **Player Interface**: Mobile-friendly interface for joining and playing quizzes
- **Real-time Updates**: WebSocket communication for live quiz state
- **Responsive Design**: Works on desktop, tablet, and mobile devices

## Development Guidelines

1. Use TypeScript for all components and functions
2. Follow the component structure outlined in the project organization
3. Implement proper error handling and loading states
4. Write tests for critical functionality
5. Use MUI components for consistent styling

## Learn More

See the [Frontend Architecture](../notes/Frontend.md) document for detailed information about the frontend design and
implementation.
