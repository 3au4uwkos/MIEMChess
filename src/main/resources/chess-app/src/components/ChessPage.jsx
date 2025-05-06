import React, { useState } from 'react';
import ChessBoard from './ChessPage/ChessBoard.jsx';
import './ChessPage/ChessPage.css';
import CPbuttons from './ChessPage/CPbuttons';
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

    const handleSurrender = () => {
        console.log("Игрок сдался");
    };

    const handleDraw = () => {
        console.log("Предложить ничью");
    };

    const handleNextMove = () => {
        console.log("Следующий ход");
    };

    const handlePrevMove = () => {
        console.log("Предыдущий ход");
    };

    return (
        <div className="chess-page">
            <div className="info-about-game">
                <a>Рейтинговая игра</a><br/>
                <span style={{ color: '#FE6D00' }}> suhrobdomoiZ </span><a style={{ color: '#0f47ad' }}> VS </a><span style={{ color: '#FE6D00' }}> EBUHOHLOV282 </span>
            </div>
            <div className="moves">
                <div className="movesTitle">
                    <span>Ходы:</span>
                </div>

                <div className="movesTable">


                </div>
            </div>
            <ChessBoard
                onCellClick={handleCellClick}
                board={board}
            />
            <CPbuttons
                onSurrender={handleSurrender}
                onDraw={handleDraw}
                onNextMove={handleNextMove}
                onPrevMove={handlePrevMove}
            />
        </div>
    );
};

export default ChessPage;
