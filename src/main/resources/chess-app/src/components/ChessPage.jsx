import React, { useState } from 'react';
import ChessBoard from './ChessPage/ChessBoard.jsx';
import './ChessPage/ChessPage.css';

const ChessPage = () => {
    const boardSize = 8;

    const initialBoard = [
        [
            '/figures/black_rook.svg',
            '/figures/black_knight.svg',
            '/figures/black_bishop.svg',
            '/figures/black_queen.svg',
            '/figures/black_king.svg',
            '/figures/black_bishop.svg',
            '/figures/black_knight.svg',
            '/figures/black_rook.svg'
        ],
        Array(8).fill('/figures/black_pawn.svg'),
        Array(8).fill(null),
        Array(8).fill(null),
        Array(8).fill(null),
        Array(8).fill(null),
        Array(8).fill('/figures/white_pawn.svg'),
        [
            '/figures/white_rook.svg',
            '/figures/white_knight.svg',
            '/figures/white_bishop.svg',
            '/figures/white_queen.svg',
            '/figures/white_king.svg',
            '/figures/white_bishop.svg',
            '/figures/white_knight.svg',
            '/figures/white_rook.svg'
        ]
    ];

    const [board, setBoard] = useState(initialBoard);
    const [selectedCell, setSelectedCell] = useState(null); // 🆕

    const handleCellClick = (row, col) => {
        const clickedFigure = board[row][col];

        // Если еще ничего не выбрано и клик по фигуре — выбираем
        if (!selectedCell && clickedFigure) {
            setSelectedCell({ row, col });
        }
        // Если уже выбрана клетка — перемещаем туда фигуру
        else if (selectedCell) {
            const newBoard = board.map((boardRow) => [...boardRow]);

            // Перемещение
            newBoard[row][col] = board[selectedCell.row][selectedCell.col];
            newBoard[selectedCell.row][selectedCell.col] = null;

            // Обновляем доску и сбрасываем выделение
            setBoard(newBoard);
            setSelectedCell(null);
        }
    };

    return (
        <div className="chess-page">
            <div className="info-about-game">

            </div>
            <div className="moves">

            </div>
            <ChessBoard
                onCellClick={handleCellClick}
                board={board}
            />
            <div className="CBbuttons-block">

            </div>
        </div>
    );
};

export default ChessPage;
