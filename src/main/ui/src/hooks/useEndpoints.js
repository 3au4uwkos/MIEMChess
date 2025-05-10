
import { useConfig } from './useConfig';

export const useHttp = () => {
    const { apiBaseUrl, wsBaseUrl } = useConfig();
    return apiBaseUrl;
};

export const useWs = () => {
    const { apiBaseUrl, wsBaseUrl } = useConfig();
    return wsBaseUrl;
};