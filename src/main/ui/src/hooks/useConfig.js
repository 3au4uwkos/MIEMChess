import { useEffect, useState } from 'react';

export const useConfig = () => {
    const [config, setConfig] = useState({
        apiBaseUrl: process.env.REACT_APP_API_URL || '',
        wsBaseUrl: process.env.REACT_APP_WS_URL || ''
    });

    useEffect(() => {
        // Если конфиг уже установлен через env переменные, не загружаем config.js
        if (config.apiBaseUrl && config.wsBaseUrl) return;

        const script = document.createElement('script');
        script.src = '/config.js';
        script.async = true;

        script.onload = () => {
            if (window.appConfig) {
                setConfig({
                    apiBaseUrl: window.appConfig.apiBaseUrl || config.apiBaseUrl,
                    wsBaseUrl: window.appConfig.wsBaseUrl || config.wsBaseUrl
                });
            }
        };

        document.body.appendChild(script);

        return () => {
            document.body.removeChild(script);
        };
    }, []); // Пустой массив зависимостей - эффект выполняется только при монтировании

    return config;
};