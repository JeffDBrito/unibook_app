import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dashboard from "../pages/Dashboard";
import { PrivateRoute } from "./PrivateRoute";
import Login from "../pages/Login";
import Books from "../pages/Books";
import Users from "../pages/Users";
import Loans from "../pages/Loans";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />

        <Route path="/dashboard" element={<PrivateRoute><Dashboard title="Dashboard"/></PrivateRoute>} />
        <Route path="/books" element={<PrivateRoute><Books title="Books"/></PrivateRoute>} />
        <Route path="/loans" element={<PrivateRoute><Loans title="Loans"/></PrivateRoute>} />
        <Route path="/users" element={<PrivateRoute><Users title="Users"/></PrivateRoute>} />

      </Routes>
    </BrowserRouter>
  );
}