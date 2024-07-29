import {
  ChevronLeft,
  ComputerIcon,
  Database,
} from "lucide-react";
import Meta from "@/components/misc/Meta";
import PageHeader from "@/components/ui/common/PageHeader";
import ContentFrame from "@/components/ui/common/ContentFrame";
import { useNavigate, useParams } from "react-router-dom";
import { CARS_ROUTE, CREATE_CAR_ROUTE } from "@/lib/consts";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form, FormControl, FormField, FormItem, FormLabel,
  FormMessage,
} from "@/components/ui/common/shadcn/form";
import { UseFormReturn, useForm } from "react-hook-form";
import * as z from "zod";
import { Button } from "@/components/ui/common/shadcn/button";
import { Input } from "@/components/ui/common/shadcn/input";
import { useGetColorsForFormField } from "@/lib/api/ColorsApi";
import { useGetMakesForFormField } from "@/lib/api/MakesApi";
import { useGetModelsForFormField } from "@/lib/api/ModelsApi";
import { useGetOwnersForFormField } from "@/lib/api/OwnersApi";
import { toast } from "sonner";
import { useCreateCar, useGetCar } from "@/lib/api/CarsApi";
import {
  Car,
} from "@/lib/types";
import { useEffect } from "react";
import { format } from "date-fns";
import SelectWithSearch from "./components/SelectWithSearch";

const namedObjectSchema = z.object({
  id: z.number().min(1, "Id is required"),
  name: z.string().min(1, "Name is required"),
});

const formSchema = z.object({
  vin: z.string().min(1),
  make: namedObjectSchema,
  model: namedObjectSchema,
  color: namedObjectSchema,
  owner: namedObjectSchema,
  year: z.string({
    required_error: "Year is required",
    invalid_type_error: "Non-numbers not allowed",
  }).min(1),
  price: z.string().min(0),
  mileage: z.string().min(0),
  registrationNumber: z.string().min(1),
  insurancePolicyNumber: z.string().min(1),
  registrationExpiration: z.string().min(1).regex(/^(\d\d\/\d\d)(\/\d\d\d\d)$/, {
    message: "Date must be in format MM/dd/yyyy",
  }),
  lastMaintenanceDate: z.string().min(1).regex(/^(\d\d\/\d\d)(\/\d\d\d\d)$/, {
    message: "Date must be in format MM/dd/yyyy",
  }),
});

export type CarForm = UseFormReturn<z.infer<typeof formSchema>, any, undefined>;

export type CarFormValues = z.infer<typeof formSchema>;

function CreateCarPage() {
  const navigate = useNavigate();
  const {
    id,
  } = useParams();
  const isCreatePage = id === CREATE_CAR_ROUTE;
  const form = useForm<CarFormValues>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      vin: "",
      model: { id: 0, name: "" },
      owner: { id: 0, name: "" },
      make: { id: 0, name: "" },
      color: { id: 0, name: "" },
      year: "",
      price: "",
      mileage: "",
      registrationNumber: "",
      insurancePolicyNumber: "",
      registrationExpiration: "",
      lastMaintenanceDate: "",
    },
  });
  // initialize with values of fetched carif there is one
  // otherwise initialize with empty values
  const resetForm = (car?: Car) => {
    form.setValue("vin", car?.vin || "zzz");
    form.setValue("year", car?.year.toString() || "");
    form.setValue("price", car?.price.toString() || "");
    form.setValue("mileage", car?.mileage.toString() || "");
    form.setValue("registrationNumber", car?.registrationNumber || "");
    form.setValue("insurancePolicyNumber", car?.insurancePolicyNumber || "");
    form.setValue("registrationExpiration", car ? format(car.registrationExpiration, "MM/dd/yyyy") : "");
    form.setValue("lastMaintenanceDate", car ? format(car.lastMaintenanceDate, "MM/dd/yyyy") : "");
    form.setValue("make", car?.make || { name: "", id: NaN });
    form.setValue("model", car?.model || { name: "", id: NaN });
    form.setValue("color", car?.color || { name: "", id: NaN });
    form.setValue("owner", car?.owner || { name: "", id: NaN });
  };
  const {
    fetchedCar,
  } = useGetCar(isCreatePage ? undefined : id);
  useEffect(() => {
    if (fetchedCar) {
      resetForm(fetchedCar);
    } else {
      resetForm();
    }
  }, [fetchedCar]);
  const {
    createCar,
  } = useCreateCar();
  const onSubmit = async (values: z.infer<typeof formSchema>) => {
    try {
      const car = await createCar(values);
      navigate(`/${CARS_ROUTE}/${car.id}`, { replace: true });
      // resetForm(car);
      // if (!entity) {
      //   router.push(successRoute || `/${params.storeId}/${entityRoute}`);
      // }
      toast.success("New car created");
    } catch (error: any) {
      toast.error(error.response.data || "Something went wrong...");
    }
  };
  const blockForm = form.formState.isSubmitting;
  const handlePressBackButton = () => {
    navigate("/");
  };
  // const {
  //   data: colors,
  //   isLoading: isLoadingGETColors,
  // } = useGetColors(form.getFieldState(""));
  const { isSubmitting } = form.formState;
  return (
    <Meta title={isCreatePage ? "Create new car" : "Edit"}>
      <main>
        <PageHeader header={isCreatePage ? "Create new car" : "Edit"} icon={<ComputerIcon />} />
        <ContentFrame mt>
          <Form {...form}>
            <form className="flex flex-col max-w-4xl m-auto" onSubmit={form.handleSubmit(onSubmit)}>
              {/* TODO: enforce make-model ownership */}
              <div className="sm:grid grid-cols-2 gap-3">
                <FormField
                  control={form.control}
                  name="make"
                  key="make"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>
                        Make
                        <span className="text-gray-400">
                          {" "}
                          &#40;select one&#41;
                        </span>
                      </FormLabel>
                      <SelectWithSearch
                        hook={useGetMakesForFormField}
                        form={form}
                        name={field.name}
                      />
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="model"
                  key="model"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>
                        Model
                        <span className="text-gray-400">
                          {" "}
                          &#40;select one&#41;
                        </span>
                      </FormLabel>
                      <SelectWithSearch
                        hook={useGetModelsForFormField}
                        form={form}
                        name={field.name}
                      />
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <FormField
                control={form.control}
                name="color"
                key="color"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>
                      Color
                      <span className="text-gray-400">
                        {" "}
                        &#40;select one&#41;
                      </span>
                    </FormLabel>
                    <SelectWithSearch
                      hook={useGetColorsForFormField}
                      form={form}
                      name={field.name}
                    />
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="owner"
                key="owner"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>
                      Owner
                      <span className="text-gray-400">
                        {" "}
                        &#40;select one&#41;
                      </span>
                    </FormLabel>
                    <SelectWithSearch
                      hook={useGetOwnersForFormField}
                      form={form}
                      name={field.name}
                    />
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="vin"
                key="vin"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>
                      VIN
                      <span className="text-gray-400">
                        {" "}
                        &#40;alphanumeric&#41;
                      </span>
                    </FormLabel>
                    <FormControl>
                      <Input
                        disabled={isSubmitting}
                        placeholder="VIN"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="year"
                key="year"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>
                      Year
                      <span className="text-gray-400">
                        {" "}
                        &#40;whole number&#41;
                      </span>
                    </FormLabel>
                    <FormControl>
                      <Input
                        disabled={isSubmitting}
                        placeholder="2024"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="price"
                key="price"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="uppercase text-xs">
                      Price
                      <span className="text-gray-400">
                        {" "}
                        &#40;USD&#41;
                      </span>
                      <span className="text-gray-400">
                        {" "}
                        &#40;whole number&#41;
                      </span>
                    </FormLabel>
                    <FormControl>
                      <Input
                        disabled={isSubmitting}
                        placeholder="10000"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="mileage"
                key="mileage"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>
                      Mileage
                      <span className="text-gray-400">
                        {" "}
                        &#40;whole number&#41;
                      </span>
                    </FormLabel>
                    <FormControl>
                      <Input
                        disabled={isSubmitting}
                        placeholder="5000"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="insurancePolicyNumber"
                key="insurancePolicyNumber"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>
                      Insurance Policy Number
                      <span className="text-gray-400">
                        {" "}
                        &#40;alphanumeric&#41;
                      </span>
                    </FormLabel>
                    <FormControl>
                      <Input
                        disabled={isSubmitting}
                        placeholder="Insurance Policy Number"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <div className="sm:grid grid-cols-2 gap-3">
                <FormField
                  control={form.control}
                  name="registrationNumber"
                  key="registrationNumber"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>
                        Registration Number
                        <span className="text-gray-400">
                          {" "}
                          &#40;alphanumeric&#41;
                        </span>
                      </FormLabel>
                      <FormControl>
                        <Input
                          disabled={isSubmitting}
                          placeholder="Registration Number"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="registrationExpiration"
                  key="registrationExpiration"
                  render={({ field }) => (
                    <FormItem className="flex flex-col justify-end">
                      <FormLabel className="h-[17px]">
                        Registration Expiration
                        <span className="text-gray-400">
                          {" "}
                          &#40;date&#41;
                        </span>
                      </FormLabel>
                      <FormControl>
                        <div className="flex">
                          <Input
                            disabled={isSubmitting}
                            placeholder="MM/dd/yyyy"
                            {...field}
                          />
                        </div>
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <FormField
                control={form.control}
                name="lastMaintenanceDate"
                key="lastMaintenanceDate"
                render={({ field }) => (
                  <FormItem className="flex flex-col justify-end">
                    <FormLabel className="h-[17px]">
                      Last Maintenance Date
                      <span className="text-gray-400">
                        {" "}
                        &#40;date&#41;
                      </span>
                    </FormLabel>
                    <FormControl>
                      <div className="flex">
                        <Input
                          disabled={isSubmitting}
                          placeholder="MM/dd/yyyy"
                          {...field}
                        />
                      </div>
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <div className="items-center mt-4 relative">
                <Button
                  disabled={blockForm}
                  variant="link"
                  type="submit"
                  className="absolute -translate-x-1/2 left-1/2 max-w-fit"
                >
                  <Database className="mr-1" size={18} />
                  Save
                </Button>
                <Button
                  type="button"
                  disabled={blockForm}
                  onClick={handlePressBackButton}
                  variant="link"
                  className="max-w-fit"
                >
                  <ChevronLeft />
                  Return
                </Button>
              </div>
            </form>
          </Form>
        </ContentFrame>
      </main>
    </Meta>
  );
}

export default CreateCarPage;
