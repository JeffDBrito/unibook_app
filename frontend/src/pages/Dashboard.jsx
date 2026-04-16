import { useAuth } from "../hooks/useAuth";
import Input from "../components/Input";
import Button from "../components/Button";
import AppLayout from "../components/layout/AppLayout";

export default function Dashboard({ title }) {
  return (
    <AppLayout title={title}>
      <p>Welcome!</p>
    </AppLayout>
  );
}