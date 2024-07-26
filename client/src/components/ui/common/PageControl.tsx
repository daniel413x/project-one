
import { cn } from "@/lib/utils";
import {
    Pagination, PaginationContent, PaginationItem, PaginationLink, PaginationNext, PaginationPrevious,
} from "./shadcn/pagination";
import { Skeleton } from "./shadcn/skeleton";

interface PageControlProps {
    page?: number;
    pages?: number;
    pageLimitReached?: boolean;
    handleSetPage: (page: number) => void;
}

function PageControl({
    page,
    pages,
    pageLimitReached,
    handleSetPage,
}: PageControlProps) {
    if (!pages || !page) {
        return null;
    }
    const finalPages = page + 2 >= pages || pages < 5;
    const firstPages = !finalPages && (page < 4);
    const pageNumbers: number[] = [];
    if (finalPages) {
        for (let p = pages - 4; p <= pages; p += 1) {
            if (p > 0) {
                pageNumbers.push(p);
            }
        }
    } else if (firstPages) {
        for (let p = 1; p <= 5; p += 1) {
            pageNumbers.push(p);
        }
    } else {
        for (let p = page - 2; p <= page + 2; p += 1) {
            pageNumbers.push(p);
        }
    }
    if (pages < 2) {
        return null;
    }
    return (
        <Pagination className="mt-14">
            <PaginationContent>
                <PaginationItem className="hidden sm:block">
                    <PaginationPrevious
                        tabIndex={page === 1 ? -1 : 0}
                        onClick={() => handleSetPage(page - 1)}
                        className={cn({
                            "text-gray-300 pointer-events-none cursor-none border-none": page === 1,
                        })}
                        data-testid="pagination-prev-button"
                    />
                </PaginationItem>
                {pageNumbers.map((num) => (
                    <PaginationItem key={num}>
                        <PaginationLink
                            tabIndex={page === num ? -1 : 0}
                            onClick={() => handleSetPage(num)}
                            isActive={page === num}
                            className={cn("cursor-pointer hover:font-bold hover:bg-white hover:text-gray-600", {
                                "pointer-events-none cursor-none bg-gray-400 text-white border-none": page === num,
                            })}
                            data-testid={`pagination-page-button-${num}`}
                        >
                            {num}
                        </PaginationLink>
                    </PaginationItem>
                ))}
                <PaginationItem className="hidden sm:block">
                    <PaginationNext
                        tabIndex={pageLimitReached ? -1 : 0}
                        onClick={() => handleSetPage(page + 1)}
                        className={cn({
                            "text-gray-300 pointer-events-none cursor-none border-none": pageLimitReached,
                        })}
                        data-testid="pagination-next-button"
                    />
                </PaginationItem>
            </PaginationContent>
        </Pagination>
    );
}

export function PageControlSkeleton() {
    return (
        <div
            className="flex m-auto gap-2 mt-14"
        >
            <Skeleton className="h-10 w-[100px] mr-2" />
            <Skeleton className="h-10 w-10" />
            <Skeleton className="h-10 w-10" />
            <Skeleton className="h-10 w-16 ml-4" />
        </div>
    );
}

export default PageControl;
