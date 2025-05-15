import React, { useState } from 'react';
import ChessBoard from './ChessPage/ChessBoard.jsx';
import './ChessPage/ChessPage.css';
import CPbuttons from './ChessPage/CPbuttons';
import { useWebSocket } from '../hooks/useWebSocket';
import { useWs } from '../hooks/useEndpoints';

const ChessPage = () => {
    const boardSize = 8;
    const [possibleMoves, setPossibleMoves] = useState([]);
    const [selectedCell, setSelectedCell] = useState(null);
    const [moveHistory, setMoveHistory] = useState([]); // история ходов

    const { wsBaseUrl } = useWs();
    const { isConnected, messages, sendMessage } = useWebSocket("ws://localhost:8080/ws/game");
    const [messageInput, setMessageInput] = useState('');

    const letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'];

    const generateFakeMoves = (row, col) => {
        const moves = [];
        for (let i = 0; i < 3; i++) {
            moves.push({
                row: Math.floor(Math.random() * 8),
                col: Math.floor(Math.random() * 8)
            });
        }
        return moves;
    };

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

    const handleCellClick = (row, col) => {
        const clickedFigure = board[row][col];

        if (!selectedCell && clickedFigure) {
            setSelectedCell({ row, col });
            const moves = generateFakeMoves(row, col);
            setPossibleMoves(moves);
            console.log(moves);
        } else if (selectedCell) {
            const validMove = possibleMoves.some(move => move.row === row && move.col === col);

            if (validMove) {
                const newBoard = board.map(boardRow => [...boardRow]);

                const fromFigure = board[selectedCell.row][selectedCell.col];
                newBoard[row][col] = fromFigure;
                newBoard[selectedCell.row][selectedCell.col] = null;

                sendMessage({
                    type: "move",
                    from: { row: selectedCell.row, col: selectedCell.col },
                    to: { row, col },
                });


                const moveNotation = `${getFigureSymbol(fromFigure)} ${letters[selectedCell.col]}${8 - selectedCell.row} → ${letters[col]}${8 - row}`;
                setMoveHistory(prevHistory => [...prevHistory, moveNotation]);

                setBoard(newBoard);
            }

            setSelectedCell(null);
            setPossibleMoves([]);
        }
    };

    const getFigureSymbol = (figurePath) => {
        if (!figurePath) return '';

        const name = figurePath.split('/').pop().split('.')[0];
        const map = {
            'white_pawn': '♟',
            'white_rook': '♜',
            'white_knight': '♞' ,
            'white_bishop': '♝',
            'white_queen': '♛',
            'white_king': '♚',
            'black_pawn': '♙',
            'black_rook': '♖',
            'black_knight': '♘',
            'black_bishop': '♗',
            'black_queen': '♕',
            'black_king': '♔'
        };
        return map[name] || '?';
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

    const handleSend = () => {
        sendMessage(messageInput);
        setMessageInput('');
    };

    return (
        <div className="chess-page">
            <div className="info-about-game">
                <a>Рейтинговая игра</a><br />
                <span style={{ color: '#FE6D00' }}> suhrobdomoiZ </span><a style={{ color: '#0f47ad' }}> VS </a><span
                style={{ color: '#FE6D00' }}> 3au4uwkos </span>
            </div>
            <div className="moves">
                <div className="movesTitle">
                    <span>Ходы:</span>
                </div>
                <div className="movesTable">
                    {Array.from({ length: Math.ceil(moveHistory.length / 2) }).map((_, i) => (
                        <div key={i}>
                            {i + 1}. {moveHistory[i * 2] || ''}{moveHistory[i * 2 + 1] ? ' | ' + moveHistory[i * 2 + 1] : ''}
                        </div>
                    ))}
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
