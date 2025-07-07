/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

import React from 'react';

const App: React.FC = () => {
    function handleClick() {
        alert("Hello World");
    }

    return (
        <div className="login-container">
            <h1 className="app-title">QUIZ MASTER</h1>
            <button className="login-button" onClick={handleClick}>Login with Twitch</button>
        </div>
    );
};

export default App;
