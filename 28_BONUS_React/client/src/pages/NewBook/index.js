import React, { useState, useEffect } from 'react';
import { useNavigate, Link, useParams } from 'react-router-dom';
import { FiArrowLeft } from "react-icons/fi";

import api from '../../services/api'

import './styles.css';
import logoImage from '../../assets/logo.svg';

export default function NewBook() {

    const [id, setId] = useState(null);
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [launchDate, setlaunchDate] = useState('');
    const [price, setPrice] = useState('');
    const { bookId } = useParams();

    const userName = localStorage.getItem('userName');
    const accessToken = localStorage.getItem('accessToken');

    const history = useNavigate();

    async function loadBook() {
        try {
            const response = await api.get(`book/v1/${bookId}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })

            let adjustDate = response.data.launchDate.split("T", 10)[0];

            setId(response.data.id);
            setTitle(response.data.title);
            setAuthor(response.data.author);
            setPrice(response.data.price);
            setlaunchDate(adjustDate);

        } catch (error) {
            alert('Error recovery Book! Try again!');
            history('/books');
        }
    }

    useEffect(() => {
        if (bookId == '0') return;
        else loadBook();
    }, [bookId])

    async function savaOrUpdate(e) {
        e.preventDefault();

        const data = {
            title,
            author,
            launchDate,
            price,
        }

        try {

            if (bookId === '0') {
                await api.post('book/v1', data, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                });
            } else {
                data.id = bookId;
                await api.put('book/v1', data, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                });
            }


            history('/books');


        } catch (error) {
            alert('Error while recording Book! Try again!')
        }
    }
    return (
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logoImage} alt="Erudio" />
                    <h1>{bookId === '0' ? 'Add New' : 'Update'} Book</h1>
                    <p>Enter the book information and click on {bookId === '0' ? "'Add'" : "'Update'"}!</p>
                    <Link className="back-link" to="/books">
                        <FiArrowLeft size={16} color="#251fc5" />
                        Return
                    </Link>
                </section>
                <form onSubmit={savaOrUpdate}>
                    <input
                        placeholder="Title"
                        value={title}
                        onChange={e => setTitle(e.target.value)}
                    />
                    <input
                        placeholder="Author"
                        value={author}
                        onChange={e => setAuthor(e.target.value)}
                    />
                    <input
                        type="date"
                        value={launchDate}
                        onChange={e => setlaunchDate(e.target.value)}
                    />
                    <input
                        placeholder="Price"
                        value={price}
                        onChange={e => setPrice(e.target.value)}
                    />

                    <button className="button" type="submit">{bookId === '0' ? 'Add' : 'Save'}</button>
                </form>
            </div>
        </div>
    );
}