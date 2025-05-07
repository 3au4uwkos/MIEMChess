import React from "react";
import './MPNavbar.css';
import MPMenu from "./MPMenu";


const MPNavbar = () => {
    return (
        <div className="MPNavbar">

            <div className="MPNavLogo">
                <img src="/img/MIEMChess_logo.png" alt="logo" />
            </div>

            <div className="MPNavbarProfile">
                <img src="/img/default_user_avatar.png" alt="ProfileAvatar"/>
                <span>Имя пользователя</span>
            </div>
            <MPMenu />
        </div>
    );
};

export default MPNavbar;
