import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { signupRequest } from "../services/signupRequest";

export default function Signup() {
    // TODO: Remove Mock signup input
    const [form, setForm] = useState({
        name: "",
        email: "",
        birthDate: "",
        login: "",
        password: "",
        confirmPassword: "",
    });

    // const [error, setError] = useState("")
    const [errors, setErrors] = useState({});
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    function handleChange(e) {
        console.log(form)
        console.log(errors)
        console.log("birthDate: ",errors.birthDate)
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });

        if (errors[name]) {
            setErrors(prev => ({
                ...prev,
                [name]: ""
            }));
        }
    }

    function resetErrors() {
        setErrors({});
    }

    async function handleSignup(e) {
        e.preventDefault();

        if (form.password.length < 8) {
            setErrors({password: "Passwords must have at least 8 characteres"});
            return;
        }

        if (form.password !== form.confirmPassword) {
            setErrors({confirmPassword: "Passwords do not match"});
            return;
        }

        setLoading(true);

        try {

            resetErrors()

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
            
            if (typeof err === "object") {
                setErrors(err);
            } 
            
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

                {/* {error && <div className="alert alert-danger">{error}</div>} */}

                <form onSubmit={handleSignup}>
                    <div className="mb-3">
                        <label className="form-label">Name</label>
                        <input name="name" className={`form-control ${errors.name ? "is-invalid" : ""}`} value={form.name} onChange={handleChange} required />
                        {errors.name && ( <span className="invalid-feedback">{errors.name}</span> )}
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Email</label>
                        <input name="email" type="email" className={`form-control ${errors.email ? "is-invalid" : ""}`} value={form.email} onChange={handleChange} required />
                        {errors.email && ( <span className="invalid-feedback"> {errors.email} </span> )}
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Birth date</label>
                        <input name="birthDate" type="date" className={`form-control ${errors.birthDate ? "is-invalid" : ""}`} value={form.birthDate} onChange={handleChange} required/>
                        {errors.birthDate && ( <span className="invalid-feedback"> {errors.birthDate} </span> )}
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Login</label>
                        <input name="login" className={`form-control ${errors.login ? "is-invalid" : ""}`} value={form.login} onChange={handleChange} required />
                        {errors.birthDate && ( <span className="invalid-feedback"> {errors.birthDate} </span> )}
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Password</label>
                        <input name="password" type="password" className={`form-control ${errors.password ? "is-invalid" : ""}`} value={form.password} onChange={handleChange} required />
                        {errors.password && ( <span className="invalid-feedback"> {errors.password} </span> )}
                    </div>

                    <div className="mb-4">
                        <label className="form-label">Confirm password</label>
                        <input name="confirmPassword" type="password" className={`form-control ${errors.confirmPassword ? "is-invalid" : ""}`} value={form.confirmPassword} onChange={handleChange} required />
                        {errors.confirmPassword && ( <span className="invalid-feedback"> {errors.confirmPassword} </span> )}
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