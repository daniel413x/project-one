import {
  Card, CardContent, CardHeader, CardTitle,
} from "@/components/ui/common/shadcn/card";
import { Skeleton } from "@/components/ui/common/shadcn/skeleton";
import CollapsibleSection from "./CollapsibleSection";
import StatUl from "./StatUl";
import SkeletonHeader from "./SkeletonHeader";

interface SalesInformationProps {
  totalOrders?: number;
}

function SalesInformation({
  totalOrders,
}: SalesInformationProps) {
  return (
    <CollapsibleSection header="Sales information">
      <StatUl>
        <li>
          <Card>
            <CardHeader>
              <CardTitle>
                Total orders
              </CardTitle>
            </CardHeader>
            <CardContent>
              {totalOrders}
            </CardContent>
          </Card>
        </li>
      </StatUl>
    </CollapsibleSection>
  );
}

export function SalesInformationSkeleton() {
  return (
    <div className="h-[182px]">
      <SkeletonHeader />
      <StatUl>
        <Skeleton className="h-[114px] w-full" />
      </StatUl>
    </div>
  );
}

export default SalesInformation;
