import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import Login from "./pages/Login";
import Books from "./pages/Books";
import NewBook from "./pages/NewBook";

export default function RoutesPage(){
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" Component={Login}/>
                <Route path="/books" Component={Books}/>
                <Route path="/books/new/:bookId" Component={NewBook}/> 
            </Routes>
        </BrowserRouter>
    );
}