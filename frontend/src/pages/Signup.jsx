import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { signupRequest } from "../services/signupRequest";

export default function Signup() {
    const [form, setForm] = useState({
        name: "Jeff Brito",
        email: "jeff@email.com",
        birthDate: "24/12/2000",
        login: "jeff",
        password: "12345678",
        confirmPassword: "12345678",
    });

    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    function handleChange(e) {
        console.log(form)
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });
    }

    async function handleSignup(e) {
        e.preventDefault();


        setError("");

        if (form.password.length < 8) {
            setError("Passwords must have at least 8 characteres");
            return;
        }

        if (form.password !== form.confirmPassword) {
            setError("Passwords do not match");
            return;
        }


        setLoading(true);

        try {
            await signupRequest({
                name: form.name,
                email: form.email,
                birthDate: form.birthDate,
                login: form.login,
                password: form.password,
            });

            navigate("/", {
                state: {
                    accountCreated: true
                }
            });
        } catch (err) { // TODO: Assign error message to each field, backend is already set on this. EX: err.birthDate
            setError(err.message || "Error creating account");
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="loginPage container vh-100 d-flex align-items-center justify-content-center">
            <div className="card shadow-sm p-4" style={{ width: "100%", maxWidth: "520px" }}>
                <div className="text-center mb-4">
                    <h2 className="fw-bold logo">UniBook</h2>
                    <p className="text-muted mb-0">Create your account</p>
                </div>

                {error && <div className="alert alert-danger">{error}</div>}

                <form onSubmit={handleSignup}>
                    <div className="mb-3">
                        <label className="form-label">Name</label>
                        <input name="name" className="form-control" value={form.name} onChange={handleChange} required />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Email</label>
                        <input name="email" type="email" className="form-control" value={form.email} onChange={handleChange} required />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Birth date</label>
                        <input name="birthDate" type="date" className="form-control" value={form.birthDate} onChange={handleChange} required />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Login</label>
                        <input name="login" className="form-control" value={form.login} onChange={handleChange} required />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Password</label>
                        <input name="password" type="password" className="form-control" value={form.password} onChange={handleChange} required />
                    </div>

                    <div className="mb-4">
                        <label className="form-label">Confirm password</label>
                        <input name="confirmPassword" type="password" className="form-control" value={form.confirmPassword} onChange={handleChange} required />
                    </div>

                    <button className="btn btn-primary w-100" disabled={loading}>
                        {loading ? "Creating account..." : "Create account"}
                    </button>
                </form>

                <p className="text-center mt-3 mb-0">
                    Already have an account? <Link to="/">Login</Link>
                </p>
            </div>
        </div>
    );
}