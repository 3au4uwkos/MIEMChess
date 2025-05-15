import React from 'react';
import "./MPLoadingScreen.css";

const LoadingScreen = ({ onCancel }) => {
    return (
        <div className="LS">
            <div className="LSwindow">
                <h1>Идет поиск игры<br/> Пожалуйста, подождите</h1>

                <div className="LSloader">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>

                <button className="LSexitButton" onClick={onCancel}>
                    <img src="/img/cross.svg" alt="Отмена" />
                    <span>Отмена</span>
                </button>
            </div>
        </div>
    );
}

export default LoadingScreen;
