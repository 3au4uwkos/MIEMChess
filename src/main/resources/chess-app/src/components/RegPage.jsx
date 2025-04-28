import React from "react";
import '../RegPage.css';


const RegPage = () => {


    return (
        <div className="RegPage">
            <div className="UpperRegPage">
                <div className="logos">
                    <img src="/img/HSE_logo.png" alt="HSE" />
                    <img src="/img/MIEM_logo.png" alt="MIEM" />
                    <img src="/img/MIEMChess_logo.png" alt="MIEMChess" />
                </div>
            </div>

            <div className="contentBlocksReg">
                {/* Левая часть */}
                <div className="leftBlockReg">
                    <h1>
                        ЭТО <br />
                        НЕ <br />
                        ПРОСТО <br />
                        <span className="highlighted">ШАХМАТЫ</span>
                    </h1>
                    <p className="descTextReg">
                        ЭТО <span className="highlighted">КУРСАЧ</span> ДВУХ ПЕРВОКУРСНИКОВ
                        МИЭМа НИУ ВШЭ
                    </p>
                </div>

                {/* Правая часть */}
                <div className="rightBlockReg">
                    <h2>
                        <span className="highlighted">ЗАПОЛНИТЕ</span> ДАННЫЕ<br /> ПОЛЬЗОВАТЕЛЯ
                    </h2>

                    <form className="regForm">
                        <label>Имя пользователя:</label>
                        <input type="text" />

                        <label>Пароль:</label>
                        <input type="password" />

                        <label>Повторите пароль:</label>
                        <input type="password" />

                        <button type="submit">РЕГИСТРАЦИЯ</button>
                    </form>

                    <div className="authBlock">
                        <p className="authText">Уже есть аккаунт?</p>
                        <span className="authLink" >Войти</span>
                    </div>
                </div>
            </div>
        </div>
    );
}
export default RegPage;
