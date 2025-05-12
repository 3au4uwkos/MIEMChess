import { useEffect, useRef, useState } from 'react';

export const useWebSocket = (url) => {
    const [isConnected, setIsConnected] = useState(false);
    const [messages, setMessages] = useState([]);
    const wsRef = useRef(null);

    useEffect(() => {
        const connect = () => {
            wsRef.current = new WebSocket(url);

            wsRef.current.onopen = () => {
                console.log('WebSocket connected');
                setIsConnected(true);
            };

            wsRef.current.onmessage = (event) => {
                try {
                    const data = JSON.parse(event.data); // Assuming JSON messages
                    setMessages(prev => [...prev, data]);
                } catch (e) {
                    console.error("Failed to parse message:", event.data, e);
                    setMessages(prev => [...prev, { type: "error", message: "Invalid message format" }]);
                }
            };

            wsRef.current.onclose = (event) => {
                console.log('WebSocket disconnected:', event.code, event.reason);
                setIsConnected(false);
                // Consider attempting a reconnection after a delay
                // if (event.code !== 1000) { // 1000 indicates a normal closure
                //     setTimeout(connect, 3000); // Reconnect after 3 seconds
                // }
            };

            wsRef.current.onerror = (error) => {
                console.error('WebSocket error:', error);
            };
        };

        connect();

        return () => {
            if (wsRef.current) {
                wsRef.current.close();
            }
        };
    }, [url]);

    const sendMessage = (message) => {
        if (wsRef.current && isConnected) {
            wsRef.current.send(JSON.stringify(message)); // Ensure sending JSON
        }
    };

    return { isConnected, messages, sendMessage };
};