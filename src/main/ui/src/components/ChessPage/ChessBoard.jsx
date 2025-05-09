import React from 'react';
import './ChessBoard.css';

const ChessBoard = ({ onCellClick, board, selectedCell, possibleMoves}) => {
    const boardSize = 8;

    const renderBoard = () => {
        const cells = [];

        for (let row = 0; row < boardSize; row++) {
            for (let col = 0; col < boardSize; col++) {
                const isDark = (row + col) % 2 === 1;
                const isSelected = selectedCell && selectedCell.row === row && selectedCell.col === col;
                const cellClass = `cell ${isDark ? 'dark' : 'light'} ${isSelected ? 'selected' : ''}`;
                const figure = board[row][col];
                const isMoveAvailable = possibleMoves.some(move => move.row === row && move.col === col);
                cells.push(
                    <div
                        key={`${row}-${col}`}
                        className={cellClass}
                        onClick={() => onCellClick(row, col)}
                    >
                        {figure && (
                            <img src={figure} alt="figure" className="figure" />
                        )}
                        {isMoveAvailable && <div className="move-indicator"></div>}
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
