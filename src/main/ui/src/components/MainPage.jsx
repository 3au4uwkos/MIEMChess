import React, { useState } from "react";
import MPNavbar from "./MainPage/MPNavbar";
import MPLeaders from "./MainPage/MPLeaders";
import MPLastGame from "./MainPage/MPLastGame";
import MPUserStats from "./MainPage/MPUserStats";
import LoadingScreen from "./MainPage/LoadingScreen";
import "./MainPage/MainPage.css";

const MainPage = () => {
    const [isLoading, setIsLoading] = useState(false);

    const handleFightClick = () => {
        setIsLoading(true);
    };

    const handleCancel = () => {
        setIsLoading(false);
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
