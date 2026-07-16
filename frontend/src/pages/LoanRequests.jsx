import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";

import { toast } from "react-toastify";

import {
    getLoanRequests,
    approveLoanRequest,
    rejectLoanRequest
} from "../services/loanRequests";

import { usePaginatedTable } from "../hooks/usePaginatedTable";

export default function LoanRequests({ title }) {

    const {
        data: requests,
        loading,
        error,

        page,
        totalPages,

        search,
        setSearch,

        setPage,
        reload

    } = usePaginatedTable(getLoanRequests);

    async function handleApprove(request){

        try{

            await approveLoanRequest(request.id);

            toast.success("Loan request approved");

            reload();

        }catch(err){

            toast.error(
                err.message ??
                "Error approving request"
            );

        }

    }

    async function handleReject(request){

        try{

            await rejectLoanRequest(request.id);

            toast.success("Loan request rejected");

            reload();

        }catch(err){

            toast.error(
                err.message ??
                "Error rejecting request"
            );

        }

    }

    const columns = [

        {
            key:"id",
            label:"ID",
            accessor:"id"
        },

        {
            key:"user",
            label:"User",
            render:r => r.userName
        },

        {
            key:"login",
            label:"Login",
            render:r => r.userLogin
        },

        {
            key:"book",
            label:"Book",
            render:r => r.bookTitle
        },

        {
            key:"isbn",
            label:"ISBN",
            render:r => r.isbn
        },

        {
            key:"requestedAt",
            label:"Requested At",
            render:r => new Date(r.requestedAt)
                .toLocaleDateString()
        },

        {
            key:"actions",
            label:"Actions",

            render:r=>(

                <div className="d-flex gap-2">

                    <button
                        className="btn btn-success btn-sm"
                        onClick={()=>handleApprove(r)}
                    >
                        Approve
                    </button>

                    <button
                        className="btn btn-danger btn-sm"
                        onClick={()=>handleReject(r)}
                    >
                        Reject
                    </button>

                </div>

            )

        }

    ];

    return(

        <AppLayout title={title}>

            <Table

                columns={columns}

                data={requests}

                loading={loading}

                error={error}

                page={page}

                totalPages={totalPages}

                onPageChange={setPage}

                search={search}

                onSearchChange={setSearch}

                searchPlaceholder="Search by user or book..."

            />

        </AppLayout>

    );

}