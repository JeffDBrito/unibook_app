import AppLayout from "../components/layout/AppLayout";

export default function Management({ title }) {

  return (
    <AppLayout title={title}>
      <h2 style={{ marginBottom: "20px" }}>Management</h2>      
    </AppLayout>
  );
}