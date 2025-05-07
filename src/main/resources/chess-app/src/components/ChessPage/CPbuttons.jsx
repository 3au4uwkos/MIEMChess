import React from "react";

const CPbuttons = ({ onSurrender, onDraw, onNextMove, onPrevMove }) => {
    return (
        <div className="CBbuttons">
            <button onClick={onSurrender}>
                <img src="/img/surrender-flag.svg" alt="Сдаться" />
            </button>
            <button onClick={onDraw}>
                <img src="/img/draw-icon.svg" alt="Ничья" />
            </button>
            <button onClick={onPrevMove}>
                <img src="/img/left-arrow.svg" alt="Предыдущий ход" />
            </button>
            <button onClick={onNextMove}>
                <img src="/img/right-arrow.svg" alt="Следующий ход" />
            </button>

        </div>
    );
};

export default CPbuttons;
