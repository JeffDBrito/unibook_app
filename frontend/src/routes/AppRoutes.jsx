import { BrowserRouter, Routes, Route } from "react-router-dom";
import { PrivateRoute } from "./PrivateRoute";

// Pages
import Login from "../pages/Login";
import Signup from "../pages/Signup";
import Dashboard from "../pages/Dashboard";
import Books from "../pages/Books";
import Copies from "../pages/Copies";
import Publishers from "../pages/Publishers"; 
import Categories from "../pages/Categories"; 
import Loans from "../pages/Loans";
import Fines from "../pages/Fines"; 
import Users from "../pages/Users";
import Authors from "../pages/Authors"; 
import Management from "../pages/Management"; 

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>

        // Login
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        
        // Pages
        <Route path="/dashboard" element={<PrivateRoute><Dashboard title="Dashboard"/></PrivateRoute>} />
        <Route path="/books" element={<PrivateRoute><Books title="Books"/></PrivateRoute>} />
        <Route path="/copies" element={<PrivateRoute><Copies title="Copies"/></PrivateRoute>} />
        <Route path="/publishers" element={<PrivateRoute><Publishers title="Publishers"/></PrivateRoute>} />
        <Route path="/categories" element={<PrivateRoute><Categories title="Categories"/></PrivateRoute>} /> 
        <Route path="/loans" element={<PrivateRoute><Loans title="Loans"/></PrivateRoute>} />
        <Route path="/fines" element={<PrivateRoute><Fines title="Fines"/></PrivateRoute>} /> // fines
        <Route path="/users" element={<PrivateRoute><Users title="Users"/></PrivateRoute>} />
        <Route path="/authors" element={<PrivateRoute><Authors title="Authors"/></PrivateRoute>} /> // authors
        <Route path="/management" element={<PrivateRoute><Management title="Management"/></PrivateRoute>} /> // management

      </Routes>
    </BrowserRouter>
  );
}