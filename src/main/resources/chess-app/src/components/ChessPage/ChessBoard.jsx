import React from 'react';
import './ChessBoard.css';

const ChessBoard = ({ onCellClick, board }) => {
    const boardSize = 8;

    const renderBoard = () => {
        const cells = [];

        for (let row = 0; row < boardSize; row++) {
            for (let col = 0; col < boardSize; col++) {
                const isDark = (row + col) % 2 === 1;
                const cellClass = isDark ? 'cell dark' : 'cell light';
                const figure = board[row][col];

                cells.push(
                    <div
                        key={`${row}-${col}`}
                        className={cellClass}
                        onClick={() => onCellClick(row, col)}
                    >
                        {figure && (
                            <img src={figure} alt="figure" className="figure" />
                        )}
                    </div>
                );
            }
        }

        return cells;
    };

    return (
        <div className="board">
            {renderBoard()}
        </div>
    );
};

export default ChessBoard;
