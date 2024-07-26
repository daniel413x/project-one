import { type ClassValue, clsx } from "clsx";
import { twMerge } from "tailwind-merge";
import { MenuItem } from "../types";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export const priceToInt = (val: number | string) => Number(val) * 100;

export const intToPrice = (val: number | string) => Number((Number(val) * 0.01)).toFixed(2);

export const menuItemsWithDecimalPrice = (menuItems: MenuItem[]) => menuItems.map((item) => ({
  ...item,
  price: Number(intToPrice(item.price)),
}));

export const menuItemsWithIntPrice = (menuItems: MenuItem[]) => menuItems.map((item) => ({
  ...item,
  price: priceToInt(item.price),
}));

export const errorCatch = (error: any): string => {
  if (typeof error === "string") {
    return error;
  }
  if (error.response && error.response.data) {
    if (typeof error.response.data.message === "object") {
      return error.response.data.message[0];
    }
    return error.response.data.message;
  }
  return error.message;
};
