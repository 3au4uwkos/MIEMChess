import { useEffect, useRef, useState } from 'react';

export const useWebSocket = (url) => {
    const [isConnected, setIsConnected] = useState(false);
    const [messages, setMessages] = useState([]);
    const wsRef = useRef(null);

    useEffect(() => {
        // Создаем соединение
        wsRef.current = new WebSocket(url);

        wsRef.current.onopen = () => {
            console.log('WebSocket connected');
            setIsConnected(true);
        };

        wsRef.current.onmessage = (event) => {
            setMessages(prev => [...prev, event.data]);
        };

        wsRef.current.onclose = () => {
            console.log('WebSocket disconnected');
            setIsConnected(false);
        };

        wsRef.current.onerror = (error) => {
            console.error('WebSocket error:', error);
        };

        // Очистка при размонтировании
        return () => {
            if (wsRef.current) {
                wsRef.current.close();
            }
        };
    }, [url]);

    const sendMessage = (message) => {
        if (wsRef.current && isConnected) {
            wsRef.current.send(message);
        }
    };

    return { isConnected, messages, sendMessage };
};