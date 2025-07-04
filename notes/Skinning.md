# Skinning and Branding

## Overview

This document outlines the approach for customizing the visual appearance of the Twitch Quiz Game System. While this
functionality is considered optional for the initial implementation, this specification defines how the system could
support branding and theming in future iterations.

## Theming Architecture

### Base Theme

The application will be built with a flexible theming system using Material-UI's ThemeProvider. The default theme will
feature:

```typescript
// src/theme/defaultTheme.ts
import {createTheme} from '@mui/material/styles';

export const defaultTheme = createTheme({
    palette: {
        primary: {
            main: '#6441A4', // Twitch purple
            light: '#9373C5',
            dark: '#3D2975',
            contrastText: '#FFFFFF',
        },
        secondary: {
            main: '#F0F0F0',
            light: '#FFFFFF',
            dark: '#C8C8C8',
            contrastText: '#000000',
        },
        background: {
            default: '#F9F9F9',
            paper: '#FFFFFF',
        },
        text: {
            primary: '#333333',
            secondary: '#6B6B6B',
        },
        error: {
            main: '#FF5252',
        },
        success: {
            main: '#4CAF50',
        },
        warning: {
            main: '#FFC107',
        },
        info: {
            main: '#2196F3',
        },
    },
    typography: {
        fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
        h1: {
            fontSize: '2.5rem',
            fontWeight: 700,
        },
        h2: {
            fontSize: '2rem',
            fontWeight: 600,
        },
        h3: {
            fontSize: '1.75rem',
            fontWeight: 600,
        },
        h4: {
            fontSize: '1.5rem',
            fontWeight: 600,
        },
        h5: {
            fontSize: '1.25rem',
            fontWeight: 500,
        },
        h6: {
            fontSize: '1rem',
            fontWeight: 500,
        },
        button: {
            textTransform: 'none',
            fontWeight: 500,
        },
    },
    shape: {
        borderRadius: 8,
    },
    components: {
        MuiButton: {
            styleOverrides: {
                root: {
                    borderRadius: 8,
                    padding: '8px 16px',
                },
                containedPrimary: {
                    '&:hover': {
                        backgroundColor: '#3D2975',
                    },
                },
            },
        },
        MuiCard: {
            styleOverrides: {
                root: {
                    boxShadow: '0px 4px 12px rgba(0, 0, 0, 0.05)',
                    borderRadius: 12,
                },
            },
        },
    },
});
```

## Customization Levels

### Level 1: Color Customization

Quizmasters will be able to customize primary colors and accent colors:

```typescript
// src/types/theme.ts
export interface ThemeCustomization {
    primaryColor: string;
    secondaryColor: string;
    backgroundColor: string;
    textColor: string;
    logoUrl?: string;
}

// src/theme/createCustomTheme.ts
export const createCustomTheme = (customization: ThemeCustomization) => {
    return createTheme({
        ...defaultTheme,
        palette: {
            ...defaultTheme.palette,
            primary: {
                main: customization.primaryColor,
                light: lighten(customization.primaryColor, 0.2),
                dark: darken(customization.primaryColor, 0.2),
                contrastText: getContrastText(customization.primaryColor),
            },
            secondary: {
                main: customization.secondaryColor,
                light: lighten(customization.secondaryColor, 0.2),
                dark: darken(customization.secondaryColor, 0.2),
                contrastText: getContrastText(customization.secondaryColor),
            },
            background: {
                default: customization.backgroundColor,
                paper: lighten(customization.backgroundColor, 0.05),
            },
            text: {
                primary: customization.textColor,
                secondary: fade(customization.textColor, 0.7),
            },
        },
    });
};
```

### Level 2: Logo and Branding

Quizmasters will be able to upload custom logos and brand elements:

```typescript
// src/components/branding/CustomLogo.tsx
export const CustomLogo: React.FC<{ logoUrl?: string }> = ({logoUrl}) => {
    if (!logoUrl) {
        return <DefaultLogo / >;
    }

    return (
        <Box
            component = "img"
    src = {logoUrl}
    alt = "Quiz Logo"
    sx = {
    {
        maxHeight: 60,
            maxWidth
    :
        200,
            objectFit
    :
        'contain',
    }
}
    onError = {(e)
=>
    {
        // Fallback to default logo if custom logo fails to load
        (e.target as HTMLImageElement).src = '/assets/default-logo.png';
    }
}
    />
)
    ;
};
```

### Level 3: CSS Customization (Future Enhancement)

Advanced users could inject custom CSS for deeper customization:

```typescript
// src/components/branding/CustomCssInjector.tsx
export const CustomCssInjector: React.FC<{ customCss?: string }> = ({customCss}) => {
    if (!customCss) {
        return null;
    }

    return (
        <style type = "text/css" >
            {customCss}
            < /style>
    );
};
```

## UI for Theme Customization

```
+--------------------------------------------------+
| Logo   Dashboard | Quizzes | Settings   User ▼   |
+--------------------------------------------------+
| Settings > Appearance                             |
|                                                  |
| Quiz Branding                                    |
| +--------------------------------------------------+
| | Logo                                             |
| | [Preview area]   [Upload Logo]  [Remove]         |
| |                                                  |
| | Colors                                           |
| | Primary:  [#6441A4] [Color Picker]               |
| | Secondary: [#F0F0F0] [Color Picker]              |
| | Background: [#F9F9F9] [Color Picker]             |
| | Text: [#333333] [Color Picker]                   |
| |                                                  |
| | Preview                                          |
| | +-------------------------------------------+    |
| | |                                           |    |
| | |  [Live preview of customization changes]  |    |
| | |                                           |    |
| | +-------------------------------------------+    |
| |                                                  |
| | [Save Changes]  [Reset to Default]               |
| +--------------------------------------------------+
```

## Database Schema for Themes

```sql
CREATE TABLE quiz_theme
(
    id               SERIAL PRIMARY KEY,
    quiz_id          INTEGER    NOT NULL,
    primary_color    VARCHAR(7) NOT NULL DEFAULT '#6441A4',
    secondary_color  VARCHAR(7) NOT NULL DEFAULT '#F0F0F0',
    background_color VARCHAR(7) NOT NULL DEFAULT '#F9F9F9',
    text_color       VARCHAR(7) NOT NULL DEFAULT '#333333',
    logo_url         VARCHAR(255),
    custom_css       TEXT,
    created_at       TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quiz_id) REFERENCES quiz (id) ON DELETE CASCADE
);
```

## Theme Application

### Quizmaster Interface

The quizmaster interface always uses the default theme for consistency and ease of use, regardless of individual quiz
themes.

### Player Interface

Player interface dynamically loads the theme associated with the current quiz:

```typescript
// src/contexts/ThemeContext.tsx
export const ThemeProvider: React.FC<PropsWithChildren<{ quizId?: string }>> = ({quizId, children}) => {
    const [customTheme, setCustomTheme] = useState<ThemeCustomization | null>(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (!quizId) {
            return;
        }

        const loadTheme = async () => {
            setLoading(true);
            try {
                const themeData = await themeService.getQuizTheme(quizId);
                setCustomTheme(themeData);
            } catch (error) {
                console.error('Failed to load theme', error);
                setCustomTheme(null);
            } finally {
                setLoading(false);
            }
        };

        loadTheme();
    }, [quizId]);

    const theme = useMemo(() => {
        if (customTheme) {
            return createCustomTheme(customTheme);
        }
        return defaultTheme;
    }, [customTheme]);

    return (
        <MuiThemeProvider theme = {theme} >
        {customTheme?.logoUrl && <CustomLogo logoUrl = {customTheme.logoUrl}
    />}
    {
        customTheme?.customCss && <CustomCssInjector customCss = {customTheme.customCss}
        />}
        {
            children
        }
        </MuiThemeProvider>
    )
        ;
    }
    ;
```

## Implementation Phases

### Phase 1 (Initial Release)

- Default theme implementation only
- No customization options

### Phase 2 (First Enhancement)

- Basic color customization
- Logo upload functionality

### Phase 3 (Future Enhancement)

- Advanced CSS customization
- Theme templates and presets
- Additional styling options (fonts, button styles, etc.)

## Technical Considerations

### Image Storage

Logo images will be stored in Digital Ocean Spaces or similar object storage:

- Format requirements: PNG, JPG, SVG
- Size limit: 500KB
- Dimensions: Recommend 400x120px, maintaining aspect ratio
- Automatic optimization for web delivery

### Performance Optimization

- Theme settings cached client-side
- Optimized custom theme generation
- Theme settings saved in local storage for returning users

### Accessibility Requirements

- Automatic contrast checking for color combinations
- Warnings for non-accessible color choices
- Enforced minimum contrast ratios for text legibility

## Limitations and Restrictions

- Custom CSS will be sanitized to prevent XSS attacks
- Certain UI elements will be protected from customization for usability
- Animations and transitions will have reasonable limits
- Custom fonts limited to web-safe fonts or Google Fonts
