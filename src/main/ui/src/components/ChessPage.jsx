import React, { useState } from 'react';
import ChessBoard from './ChessPage/ChessBoard.jsx';
import './ChessPage/ChessPage.css';
import CPbuttons from './ChessPage/CPbuttons';
const ChessPage = () => {

    const boardSize = 8;
    const [possibleMoves, setPossibleMoves] = useState([]);//возможные ходы
    const [selectedCell, setSelectedCell] = useState(null);  // запоминает выбранную клетку

    const generateFakeMoves = (row, col) => {//генерируем ходы
        const moves = [];
        for (let i = 0; i < 3; i++) {
            moves.push({
                row: Math.floor(Math.random() * 8),
                col: Math.floor(Math.random() * 8)
            });
        }
        return moves;
    };
    const initialBoard = [//фигурки
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


    const handleCellClick = (row, col) => {
        const clickedFigure = board[row][col];

        if (!selectedCell && clickedFigure) {
            // Если фигура выбрана, то выбираем эту клетку и генерируем возможные ходы
            setSelectedCell({ row, col });
            const moves = generateFakeMoves(row, col);
            setPossibleMoves(moves);
            console.log(moves);
        } else if (selectedCell) {
            // Проверка, является ли кликнутый ход допустимым
            const validMove = possibleMoves.some(move => move.row === row && move.col === col);

            if (validMove) {
                // Если ход допустимый, перемещаем фигуру
                const newBoard = board.map((boardRow) => [...boardRow]);

                newBoard[row][col] = board[selectedCell.row][selectedCell.col];
                newBoard[selectedCell.row][selectedCell.col] = null;

                setBoard(newBoard);
            }

            // После хода или отмены выделения очищаем выбранную клетку и возможные ходы
            setSelectedCell(null);
            setPossibleMoves([]);
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
    //так тут у нас в info-about-game лежит тип игры и ники игроков
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
                selectedCell={selectedCell}
                possibleMoves={possibleMoves}
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