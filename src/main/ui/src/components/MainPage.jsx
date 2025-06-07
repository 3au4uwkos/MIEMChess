import React, { useState } from "react";
import MPNavbar from "./MainPage/MPNavbar";
import MPLeaders from "./MainPage/MPLeaders";
import MPLastGame from "./MainPage/MPLastGame";
import MPUserStats from "./MainPage/MPUserStats";
import LoadingScreen from "./MainPage/LoadingScreen";
import { useNavigate } from "react-router-dom";
import "./MainPage/MainPage.css";
import axios from "axios";
import {useHttp, useWs} from '../hooks/useEndpoints';
import ChessPage from "./ChessPage";

const MainPage = () => {
    const [isLoading, setIsLoading] = useState(false);
    const apiBaseUrl = useHttp();
    const { wsBaseUrl } = useWs();
    const token = localStorage.getItem('authToken');
    const username = localStorage.getItem('username');
    const navigate = useNavigate();


    const handleFightClick = async () => {
        setIsLoading(true);
        const response = await axios.post(
            `${apiBaseUrl}/game?game=1&username=${username}`,
            null,
            {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            }
        );

        if(response.status === 200){

            setIsLoading(false);

            navigate(`/chess`, {
                state: {
                    color: response.data.match.color,
                    opponent: response.data.match.opponent,
                    gameID: response.data.match.gameID
                }
            });

        }
    };

    const handleCancel = async () => {
        setIsLoading(false);
        await axios.post(
            `${apiBaseUrl}/game?game=0&username=${username}`,
            null,
            {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            }
        );
    };

    return (
        <div className="MainPage">
            <MPNavbar />
            <div className="MainPageHeader">
                <h1 className="MainPageTitle">
                    Время <span className="highlighted"><br />побеждать</span>
                </h1>
                <button className="MainPageButton" onClick={handleFightClick}>
                    В бой
                </button>
            </div>
            <MPLeaders />
            <MPLastGame />
            <MPUserStats />

            {isLoading && <LoadingScreen onCancel={handleCancel} />}
        </div>
    );
};

export default MainPage;
