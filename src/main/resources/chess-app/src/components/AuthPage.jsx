import React from "react";
import '../AuthPage.css';

const AuthPage = () => {
    return (
        <div className="AuthPage">
            <div className="UpperAuthPage">
                <div className="logos">
                    <img src="/img/HSE_logo.png" alt="HSE" />
                    <img src="/img/MIEM_logo.png" alt="MIEM" />
                    <img src="/img/MIEMChess_logo.png" alt="MIEMChess" />
                </div>
            </div>

            <div className="contentBlocks">
                {/* Левая часть */}
                <div className="leftBlock">
                    <h1>
                        ЭТО <br />
                        НЕ <br />
                        ПРОСТО <br />
                        <span className="highlighted">ШАХМАТЫ</span>
                    </h1>
                    <p className="descText">
                        ЭТО <span className="highlighted">КУРСАЧ</span> ДВУХ ПЕРВОКУРСНИКОВ
                        МИЭМа НИУ ВШЭ
                    </p>
                </div>

                {/* Правая часть */}
                <div className="rightBlock">
                    <h2>
                        <span className="highlighted">ВОЙДИТЕ</span>, <br /> ЧТОБЫ ИГРАТЬ
                    </h2>

                    <form className="authForm">
                        <label>Имя пользователя:</label>
                        <input type="text" />

                        <label>Пароль:</label>
                        <input type="password" />

                        <button type="submit">ВОЙТИ</button>
                    </form>

                    <div className="registerBlock">
                        <p className="registerText">Еще нет аккаунта?</p>
                        <span className="registerLink">Зарегистрироваться</span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AuthPage;
