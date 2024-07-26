import {
  Card, CardContent, CardHeader, CardTitle,
} from "@/components/ui/common/shadcn/card";
import { Order } from "@/lib/types";
import { Skeleton } from "@/components/ui/common/shadcn/skeleton";
import CollapsibleSection from "./CollapsibleSection";
import StatUl from "./StatUl";
import SkeletonHeader from "./SkeletonHeader";

type GraphData = {
  name: string;
  sold: number;
}[];

const getMenuItemsOrderCounts = (orders?: Order[]) => {
  const graphData: GraphData = [];
  if (!orders || orders.length === 0) {
    return graphData;
  }
  const counts: { [name: string]: number } = {};
  for (let i = 0; i < orders.length; i += 1) {
    for (let j = 0; j < orders[i].cartItems.length; j += 1) {
      const {
        name, quantity,
      } = orders[i].cartItems[j];
      if (counts[name]) {
        counts[name] += quantity;
      } else {
        counts[name] = quantity;
      }
    }
  }
  const keys = Object.keys(counts);
  for (let k = 0; k < keys.length; k += 1) {
    graphData.push({
      name: keys[k],
      sold: counts[keys[k]],
    });
  }
  return graphData.sort((a, b) => b.sold - a.sold);
};

interface MenuItemsSalesProps {
  orders?: Order[];
}

function MenuItemsSales({
  orders,
}: MenuItemsSalesProps) {
  const itemsSold = getMenuItemsOrderCounts(orders);
  return (
    <CollapsibleSection header="Menu items / total sales">
      <StatUl>
        {itemsSold.map((item) => (
          <li key={item.name}>
            <Card>
              <CardHeader>
                <CardTitle>
                  {item.name}
                </CardTitle>
              </CardHeader>
              <CardContent className="">
                {item.sold}
              </CardContent>
            </Card>
          </li>
        ))}
      </StatUl>
    </CollapsibleSection>
  );
}

export function MenuItemsSalesSkeleton() {
  return (
    <div className="h-[182px]">
      <SkeletonHeader />
      <StatUl>
        <Skeleton className="h-[114px] w-full" />
        <Skeleton className="h-[114px] w-full" />
      </StatUl>
    </div>
  );
}

export default MenuItemsSales;
