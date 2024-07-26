import { Skeleton } from "@/components/ui/common/shadcn/skeleton";

function SkeletonHeader() {
  return (
    <div className="flex space-between my-10">
      <Skeleton className="w-[140px] h-8" />
      <div className="w-full" />
      <Skeleton className="w-8 h-8" />
    </div>
  );
}

export default SkeletonHeader;
