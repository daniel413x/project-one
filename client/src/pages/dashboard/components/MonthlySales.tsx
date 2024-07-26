import { Order } from "@/lib/types";
import {
  Bar, BarChart, ResponsiveContainer, XAxis, YAxis,
} from "recharts";
import { Skeleton } from "@/components/ui/common/shadcn/skeleton";
import { useMediaQuery } from "usehooks-ts";
import CollapsibleSection from "./CollapsibleSection";
import SkeletonHeader from "./SkeletonHeader";

type GraphData = {
  name: string;
  total: number;
};

type MonthRecord = { [month: string]: number };

export const getRevenueByMonth = (orders?: Order[]) => {
  const graphData: GraphData[] = [
    { name: "Jan", total: 0 },
    { name: "Feb", total: 0 },
    { name: "Mar", total: 0 },
    { name: "Apr", total: 0 },
    { name: "May", total: 0 },
    { name: "Jun", total: 0 },
    { name: "Jul", total: 0 },
    { name: "Aug", total: 0 },
    { name: "Sep", total: 0 },
    { name: "Oct", total: 0 },
    { name: "Nov", total: 0 },
    { name: "Dec", total: 0 },
  ];
  if (!orders || orders.length === 0) {
    return graphData;
  }
  const monthlyRevenue: MonthRecord = {};
  for (let i = 0; i < orders.length; i += 1) {
    const order = orders[i];
    const createdAt = new Date(order.createdAt);
    const month = createdAt.getMonth();
    monthlyRevenue[month] = (monthlyRevenue[month] || 0) + (order.totalAmount / 100);
  }
  const mKeys = Object.keys(monthlyRevenue);
  for (let k = 0; k < mKeys.length; k += 1) {
    graphData[Number(mKeys[k])].total = monthlyRevenue[mKeys[k]];
  }
  return graphData;
};

interface MonthlySalesProps {
  orders?: Order[];
}

function MonthlySales({
  orders,
}: MonthlySalesProps) {
  const sm = useMediaQuery("(min-width: 640px)");
  const md = useMediaQuery("(min-width: 768px)");
  let responsiveContainerWidth = "110%";
  if (sm) {
    responsiveContainerWidth = "105%";
  }
  if (md) {
    responsiveContainerWidth = "100%";
  }
  const revenue = getRevenueByMonth(orders);
  return (
    <CollapsibleSection header="Monthly sales">
      <ResponsiveContainer width={responsiveContainerWidth} height={350} className="relative right-12">
        <BarChart data={revenue}>
          <XAxis
            dataKey="name"
            stroke="#111"
            fontSize={12}
          />
          <YAxis
            stroke="#111"
            fontSize={12}
            tickFormatter={(v) => `$${v}`}
          />
          <Bar dataKey="total" fill="#ff9b51" radius={[4, 4, 0, 0]} />
        </BarChart>
      </ResponsiveContainer>
    </CollapsibleSection>
  );
}

export function MonthlySalesSkeleton() {
  return (
    <div className="h-[418px]">
      <SkeletonHeader />
      <div className="ps-10 flex items-end">
        <Skeleton className="h-[350px] w-full" />
      </div>
    </div>
  );
}

export default MonthlySales;
