import { BrowserRouter, Routes, Route } from "react-router-dom";
import { PrivateRoute } from "./PrivateRoute";

// Pages
import Login from "../pages/Login";
import Signup from "../pages/Signup";
import Dashboard from "../pages/Dashboard";
import Books from "../pages/books/Books";
import Copies from "../pages/Copies";
import Publishers from "../pages/Publishers"; 
import Categories from "../pages/Categories"; 
import Loans from "../pages/Loans";
import Fines from "../pages/Fines"; 
import Users from "../pages/users/Users";
import Authors from "../pages/Authors"; 
import Management from "../pages/Management"; 
import EditUser from "../pages/users/EditUser";
import CreateUser from "../pages/users/CreateUser";
import CreateBook from "../pages/books/CreateBook";
import EditBook from "../pages/books/EditBook";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>

        // Login
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        
        // Pages
        <Route path="/dashboard" element={<PrivateRoute><Dashboard title="Dashboard"/></PrivateRoute>} />
        <Route path="/copies" element={<PrivateRoute><Copies title="Copies"/></PrivateRoute>} />
        <Route path="/publishers" element={<PrivateRoute><Publishers title="Publishers"/></PrivateRoute>} />
        <Route path="/categories" element={<PrivateRoute><Categories title="Categories"/></PrivateRoute>} /> 
        <Route path="/loans" element={<PrivateRoute><Loans title="Loans"/></PrivateRoute>} />
        <Route path="/fines" element={<PrivateRoute><Fines title="Fines"/></PrivateRoute>} /> // fines
        <Route path="/authors" element={<PrivateRoute><Authors title="Authors"/></PrivateRoute>} /> // authors
        <Route path="/management" element={<PrivateRoute roles={["SUPER_ADMIN","ADMIN"]}><Management title="Management"/></PrivateRoute>} /> // management

        // Users Pages
        <Route path="/users" element={<PrivateRoute><Users title="Users"/></PrivateRoute>} />
        <Route path="/users/create" element={<PrivateRoute><CreateUser title="Create User" /></PrivateRoute>} />
        <Route path="/users/:id/edit" element={<PrivateRoute><EditUser title="Users"/></PrivateRoute>} />

        // Books Pages
        <Route path="/books" element={<PrivateRoute><Books title="Books"/></PrivateRoute>} />
        <Route path="/books/create" element={<PrivateRoute><CreateBook title="Create Book" /></PrivateRoute>} />
        <Route path="/books/:id/edit" element={<PrivateRoute><EditBook title="Edit Book" /></PrivateRoute>} />
      </Routes>
    </BrowserRouter>
  );
}