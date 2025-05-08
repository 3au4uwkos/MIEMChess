
import { useConfig } from './useConfig';

export const useHttp = () => {
    const { apiBaseUrl } = useConfig();
    return apiBaseUrl;
};

export const useWs = () => {
    const { wsBaseUrl } = useConfig();
    return wsBaseUrl;
};