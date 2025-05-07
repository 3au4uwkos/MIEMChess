import React from "react";
import MPNavbar from "./MainPage/MPNavbar";
import MPLeaders from "./MainPage/MPLeaders";
import MPLastGame from "./MainPage/MPLastGame";
import MPUserStats from "./MainPage/MPUserStats";
import "./MainPage/MainPage.css";

const MainPage = () => {
    return (
        <div className="MainPage">
            <MPNavbar />
            <div className="MainPageHeader">
                <h1 className="MainPageTitle">
                    Время <span className="highlighted"><br />побеждать</span>
                </h1>
                <button className="MainPageButton">В бой</button>
            </div>
            <MPLeaders />
            <MPLastGame />
            <MPUserStats />
        </div>
    );
};

export default MainPage;
