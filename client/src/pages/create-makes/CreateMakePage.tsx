import {
  ChevronLeft,
  CloudUpload,
  ComputerIcon,
  Trash2,
} from "lucide-react";
import Meta from "@/components/misc/Meta";
import PageHeader from "@/components/ui/common/PageHeader";
import ContentFrame from "@/components/ui/common/ContentFrame";
import { useNavigate, useParams } from "react-router-dom";
import { MAKES_ROUTE, CREATE_ROUTE } from "@/lib/consts";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form, FormControl, FormField, FormItem, FormLabel,
  FormMessage,
} from "@/components/ui/common/shadcn/form";
import { UseFormReturn, useForm } from "react-hook-form";
import * as z from "zod";
import { Button } from "@/components/ui/common/shadcn/button";
import { Input } from "@/components/ui/common/shadcn/input";
import { toast } from "sonner";
import {
  useCreateMake, useGetCarsCount, useGetMake, useUpdateMake,
} from "@/lib/api/MakesApi";
import {
  Make,
} from "@/lib/types";
import { useEffect } from "react";
import useReturnToQueryResultsCallback from "@/lib/hooks/useReturnToQueryResultsCallback";
import DeleteMakeAlertDialog from "../makes/components/DeleteMakeDialog";

const formSchema = z.object({
  name: z.string().min(1),
  logoUrl: z.string().min(1),
});

export type MakeForm = UseFormReturn<z.infer<typeof formSchema>, any, undefined>;

export type MakeFormValues = z.infer<typeof formSchema>;

function CreateMakePage() {
  const navigate = useNavigate();
  const {
    id,
  } = useParams();
  const isCreatePage = id === CREATE_ROUTE;
  const form = useForm<MakeFormValues>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name: "",
      logoUrl: "",
    },
  });
  // initialize with values of fetched make if there is one
  // otherwise initialize with empty values
  const resetForm = (make?: Make) => {
    form.setValue("name", make?.name || "");
    form.setValue("logoUrl", make?.logoUrl || "");
  };
  const {
    fetchedMake,
  } = useGetMake(isCreatePage ? null : id);
  useEffect(() => {
    if (fetchedMake) {
      resetForm(fetchedMake);
    } else {
      resetForm();
    }
  }, [fetchedMake]);
  const {
    createMake,
  } = useCreateMake();
  const {
    updateMake,
  } = useUpdateMake(id!);
  const onSubmit = async (values: z.infer<typeof formSchema>) => {
    try {
      if (fetchedMake) {
        await updateMake(values);
        toast.success("Make updated");
      } else {
        const make = await createMake(values);
        navigate(`/${MAKES_ROUTE}/${make.id}`, { replace: true });
        resetForm(make);
        toast.success("New make created");
      }
    } catch (error: any) {
      toast.error(error.response.data || "Something went wrong...");
    }
  };
  const blockForm = form.formState.isSubmitting;
  const {
    wereSearchResults,
    onPressBackButton,
  } = useReturnToQueryResultsCallback(MAKES_ROUTE);
  const { isSubmitting } = form.formState;
  const pageHeaderText = isCreatePage ? "Create new make" : `Edit ${fetchedMake?.name}`;
  const {
    data: carsCount,
  } = useGetCarsCount(fetchedMake?.name);
  return (
    <Meta title={pageHeaderText}>
      <main>
        <PageHeader header={pageHeaderText} icon={<ComputerIcon />} />
        <ContentFrame mt>
          <div className="flex flex-col max-w-4xl  m-auto">
            <div className="grid grid-cols-2 gap-3">
              <Button
                type="button"
                disabled={blockForm}
                variant="outline"
                className="justify-start mb-4 ps-0 font-bold"
                onClick={onPressBackButton}
              >
                <ChevronLeft />
                {wereSearchResults ? "Return to search results" : "Exit"}
              </Button>
            </div>
            <Form {...form}>
              <form className="flex flex-col" onSubmit={form.handleSubmit(onSubmit)}>
                <FormField
                  control={form.control}
                  name="name"
                  key="name"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>
                        Name
                        <span className="text-gray-500">
                          {" "}
                          &#40;alphanumeric&#41;
                        </span>
                      </FormLabel>
                      <FormControl>
                        <Input
                          disabled={isSubmitting}
                          placeholder="Name"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="logoUrl"
                  key="logoUrl"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>
                        Logo URL
                        <span className="text-gray-500">
                          {" "}
                          &#40;url&#41;
                          {" "}
                          &#40;alphanumeric&#41;
                        </span>
                      </FormLabel>
                      <FormControl>
                        <Input
                          disabled={isSubmitting}
                          placeholder="https://..."
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <div className=" mt-6 grid sm:grid-cols-3 gap-3">
                  <Button
                    disabled={blockForm}
                    type="submit"
                  >
                    <CloudUpload className="mr-1.5" strokeWidth={3} size={18} />
                    {!fetchedMake ? "Create" : "Save"}
                  </Button>
                  {!fetchedMake ? null : (
                    <DeleteMakeAlertDialog
                      make={fetchedMake}
                      carsCount={carsCount}
                      returnTo={`/${MAKES_ROUTE}`}
                    >
                      <Button
                        type="button"
                        disabled={blockForm}
                        variant="destructive"
                      >
                        <Trash2 />
                        Delete
                      </Button>
                    </DeleteMakeAlertDialog>
                  )}
                </div>
              </form>
            </Form>
          </div>
        </ContentFrame>
      </main>
    </Meta>
  );
}

export default CreateMakePage;
