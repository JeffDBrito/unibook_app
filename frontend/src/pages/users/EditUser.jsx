import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getUser, updateUser } from "../../services/users";
import AppLayout from "../../components/layout/AppLayout";
import { getRoles } from "../../services/roles";

export default function EditUser({title}) {
  const { id } = useParams();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    email: "",
    birthDate: "",
    password: "",
    roleIds: [],
  });

  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [roles, setRoles] = useState([]);

  useEffect(() => {
    async function loadUser() {
      try {
        const user = await getUser(id);

        setForm({
          name: user.person.name || "",
          email: user.person.email || "",
          birthDate: user.person.birthDate || "",
          login: user.login || "",
          password: "",
          roleIds: user.roleIds || [],
        });
      } finally {
        setLoading(false);
      }
    }
    
    loadUser();

    async function loadRoles() {
      const data = await getRoles();
      setRoles(data);
    }
    
    loadRoles();
  }, [id]);

  function handleChange(e) {
    const { name, value } = e.target;

    setForm(prev => ({
      ...prev,
      [name]: value,
    }));

    setErrors(prev => ({
      ...prev,
      [name]: "",
    }));
  }

  function handleRoleChange(e) {
    const roleId = Number(e.target.value);

    setForm(prev => ({
      ...prev,
      roleIds: e.target.checked
        ? [...prev.roleIds, roleId]
        : prev.roleIds.filter(id => id !== roleId),
    }));

    setErrors(prev => ({
      ...prev,
      roleIds: "",
    }));
  }

  async function handleSubmit(e) {
    e.preventDefault();

    setSaving(true);
    setErrors({});

    const payload = { ...form };

    if (!payload.password) {
      delete payload.password;
    }

    try {
      await updateUser(id, payload);
      navigate("/users");
    } catch (err) {
      setErrors(err);
    } finally {
      setSaving(false);
    }
  }

  return (
    <AppLayout title={title}>
        <div className="container">
            {
                loading ? 
                <span>Loading user details...</span> : 
                <div>
                    <h1>Edit User</h1>

                    <form onSubmit={handleSubmit} className="card p-4">
                        <div className="mb-3">
                        <label className="form-label">Name</label>
                        <input
                            name="name"
                            className={`form-control ${errors.name ? "is-invalid" : ""}`}
                            value={form.name}
                            onChange={handleChange}
                        />
                        {errors.name && <div className="invalid-feedback">{errors.name}</div>}
                        </div>

                        <div className="mb-3">
                        <label className="form-label">Email</label>
                        <input
                            name="email"
                            type="email"
                            className={`form-control ${errors.email ? "is-invalid" : ""}`}
                            value={form.email}
                            onChange={handleChange}
                        />
                        {errors.email && <div className="invalid-feedback">{errors.email}</div>}
                        </div>

                        <div className="mb-3">
                        <label className="form-label">Birth date</label>
                        <input
                            name="birthDate"
                            type="date"
                            className={`form-control ${errors.birthDate ? "is-invalid" : ""}`}
                            value={form.birthDate}
                            onChange={handleChange}
                        />
                        {errors.birthDate && <div className="invalid-feedback">{errors.birthDate}</div>}
                        </div>

                        <div className="mb-3">
                        <label className="form-label">Login</label>
                        <input
                            disabled
                            name="login"
                            className={`form-control ${errors.login ? "is-invalid" : ""}`}
                            value={form.login}
                            onChange={handleChange}
                        />
                        {errors.login && <div className="invalid-feedback">{errors.login}</div>}
                        </div>

                        <div className="mb-3">
                        <label className="form-label">Password</label>
                        <input
                            name="password"
                            type="password"
                            className={`form-control ${errors.password ? "is-invalid" : ""}`}
                            value={form.password}
                            onChange={handleChange}
                            placeholder="Enter a new password"
                        />
                        {errors.password && <div className="invalid-feedback">{errors.password}</div>}
                        </div>

                        <div className="mb-4">
                          <label className="form-label">Roles</label>

                          {roles.map(role => (
                            <div className="form-check" key={role.id}>
                              <input
                                className="form-check-input"
                                type="checkbox"
                                value={role.id}
                                checked={form.roleIds.includes(role.id)}
                                onChange={handleRoleChange}
                              />
                              <label className="form-check-label">
                                {role.name}
                              </label>
                            </div>
                          ))}

                          {errors.roleIds && (
                            <div className="text-danger mt-1">
                              {errors.roleIds}
                            </div>
                          )}
                        </div>

                        <button className="btn btn-primary" disabled={saving}>
                        {saving ? "Saving..." : "Save"}
                        </button>
                    </form>
                </div>
            }
        </div>
    </AppLayout>
  );
}