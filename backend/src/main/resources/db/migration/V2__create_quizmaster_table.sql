/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

-- Create quizmaster table
CREATE TABLE quizmasters
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(50)              NOT NULL UNIQUE,
    twitch_id  VARCHAR(50)              NOT NULL UNIQUE,
    email      VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP WITH TIME ZONE,
    CONSTRAINT valid_email CHECK (email IS NULL OR email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'
)
    );

-- Create index for quizmasters
CREATE INDEX idx_quizmaster_username ON quizmasters (username);
CREATE INDEX idx_quizmaster_twitch_id ON quizmasters (twitch_id);
