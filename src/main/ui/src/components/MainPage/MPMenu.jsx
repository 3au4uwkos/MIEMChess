import React from 'react';
import { useNavigate } from 'react-router-dom';
import "./MPMenu.css";

const Menu = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Очищаем локальное хранилище
        localStorage.removeItem('authToken');
        localStorage.removeItem('username');

        // Перенаправляем на главную страницу
        navigate('/');
    };

    return (
        <div className="MPMenu">
            <button className="MPMenuButton"><img src="/img/menu-burger.svg" alt="BurgerMenu" /></button>
            <div className="MPMenuDropdown">
                <div className="MPMenuDropdownContent">
                    <a href="#"><img src="/img/profile-icon.svg"/> Профиль</a>
                    <a href="#"><img src="/img/settings-icon.svg"/> Настройки</a>
                    <a href="#" onClick={handleLogout}><img src="/img/exit.png"/> Выход</a>
                </div>
            </div>
        </div>
    );
};

export default Menu;