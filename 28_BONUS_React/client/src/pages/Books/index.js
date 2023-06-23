import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { FiPower, FiEdit, FiTrash2 } from 'react-icons/fi';

import api from '../../services/api'

import './styles.css';
import logoImage from '../../assets/logo.svg';

export default function Books() {

    const [books, setBooks] = useState([]);
    const [page, setPage] = useState(0);


    const userName = localStorage.getItem('userName');
    const accessToken = localStorage.getItem('accessToken');

    const history = useNavigate();

    async function logout() {
        localStorage.clear();
        history('/');
    }

    async function editBook(id) {
        try {
            history(`/books/new/${id}`)
        } catch (error) {
            alert('Edit failed! Try Again.')
        }
    }

    async function deleteBook(id) {
        try {

            await api.delete(`book/v1/${id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });

            setBooks(books.filter(book => book.id !== id));

            fetchBooks();

        } catch (error) {
            alert('Delete failed! Try Again');
        }
    }

    async function fetchBooks() {
        const response = await api.get('book/v1', {
            headers: {
                Authorization: `Bearer ${accessToken}`
            },
            params: {
                page: page,
                size: 4,
                direction: 'asc'
            }
        });

        setBooks([...books, ...response.data._embedded.bookVOList])
        setPage(page + 1);

    }

    useEffect(() => {
        fetchBooks();
    }, []);

    return (
        <div className="book-container">
            <header>
                <img src={logoImage} alt="Erudio" />
                <span>Welcome,<strong>{userName.toUpperCase()}</strong>!</span>

                <Link className="button" to="/books/new/0">Add new Book</Link>

                <button type="button" onClick={logout}>
                    <FiPower size={18} color="#251FC5" />
                </button>

            </header>

            <h1>Registered Books</h1>
            <ul>
                {books.map(book => (
                    <li key={book.id}>
                        <strong>Title:</strong>
                        <p>{book.title}</p>
                        <strong>Author:</strong>
                        <p>{book.author}</p>
                        <strong>Price:</strong>
                        <p>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(book.price)}</p>
                        <strong>Release Date:</strong>
                        <p>{Intl.DateTimeFormat('pt-BR').format(new Date(book.launchDate))}</p>

                        <button onClick={() => editBook(book.id)} type="button">
                            <FiEdit size={20} color="#251FC5" />
                        </button>

                        <button onClick={() => deleteBook(book.id)} type="button">
                            <FiTrash2 size={20} color="#251FC5" />
                        </button>

                    </li>
                ))}
            </ul>

            <button className='button' onClick={fetchBooks} type='button'>Load More</button>
        </div>
    )
}