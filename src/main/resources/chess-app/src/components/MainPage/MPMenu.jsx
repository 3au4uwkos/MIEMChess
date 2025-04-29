import React from "react";
import "./MPMenu.css";

const MPMenu = () => {
    return (
        <div className="MPMenu">
            <button className="MPMenuButton"><img src="/img/menu-burger.svg" alt="BurgerMenu" /></button>
            <div className="MPMenuDropdown">
                <div className="MPMenuDropdownContent">
                    <a href="#"><img src="/img/swords.png"/> Профиль</a>
                    <a href="#"><img src="/img/settings-icon.svg"/> Настройки</a>
                    <a href="#"><img src="/img/exit.png"/> Выход</a>
                </div>
            </div>
        </div>
    );
};

export default MPMenu;