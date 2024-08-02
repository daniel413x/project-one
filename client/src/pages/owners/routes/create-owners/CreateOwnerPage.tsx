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
import { OWNERS_ROUTE, CREATE_ROUTE } from "@/lib/consts";
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
  useCreateOwner, useGetCarsCount, useGetOwner, useUpdateOwner,
} from "@/lib/api/OwnersApi";
import {
  Owner,
} from "@/lib/types";
import { useEffect } from "react";
import useReturnToQueryResultsCallback from "@/lib/hooks/useReturnToQueryResultsCallback";
import DeleteOwnerAlertDialog from "../../components/DeleteOwnerAlertDialog";

const formSchema = z.object({
  name: z.string().min(1),
  contact: z.string().min(1),
});

export type OwnerForm = UseFormReturn<z.infer<typeof formSchema>, any, undefined>;

export type OwnerFormValues = z.infer<typeof formSchema>;

function CreateOwnerPage() {
  const navigate = useNavigate();
  const {
    id,
  } = useParams();
  const isCreatePage = id === CREATE_ROUTE;
  const form = useForm<OwnerFormValues>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name: "",
      contact: "",
    },
  });
  // initialize with values of fetched owner if there is one
  // otherwise initialize with empty values
  const resetForm = (owner?: Owner) => {
    form.setValue("name", owner?.name || "");
    form.setValue("contact", owner?.contact || "");
  };
  const {
    fetchedOwner,
  } = useGetOwner(isCreatePage ? null : id);
  useEffect(() => {
    if (fetchedOwner) {
      resetForm(fetchedOwner);
    } else {
      resetForm();
    }
  }, [fetchedOwner]);
  const {
    createOwner,
  } = useCreateOwner();
  const {
    updateOwner,
  } = useUpdateOwner(id!);
  const onSubmit = async (values: z.infer<typeof formSchema>) => {
    try {
      if (fetchedOwner) {
        await updateOwner(values);
        toast.success("Owner updated");
      } else {
        const owner = await createOwner(values);
        navigate(`/${OWNERS_ROUTE}/${owner.id}`, { replace: true });
        resetForm(owner);
        toast.success("New owner created");
      }
    } catch (error: any) {
      toast.error(error.response.data || "Something went wrong...");
    }
  };
  const blockForm = form.formState.isSubmitting;
  const {
    wereSearchResults,
    onPressBackButton,
  } = useReturnToQueryResultsCallback(OWNERS_ROUTE);
  const { isSubmitting } = form.formState;
  const pageHeaderText = isCreatePage ? "Create new owner" : `Edit ${fetchedOwner?.name}`;
  const {
    data: carsCount,
  } = useGetCarsCount(fetchedOwner?.id);
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
                          &#40;alphabetical&#41;
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
                  name="contact"
                  key="contact"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>
                        Contact info
                        <span className="text-gray-500">
                          {" "}
                          &#40;alphanumeric&#41;
                        </span>
                      </FormLabel>
                      <FormControl>
                        <Input
                          disabled={isSubmitting}
                          placeholder="Email or phone number"
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
                    {!fetchedOwner ? "Create" : "Save"}
                  </Button>
                  {!fetchedOwner ? null : (
                    <DeleteOwnerAlertDialog
                      owner={fetchedOwner}
                      carsCount={carsCount}
                      returnTo={`/${OWNERS_ROUTE}`}
                    >
                      <Button
                        type="button"
                        disabled={blockForm}
                        variant="destructive"
                      >
                        <Trash2 />
                        Delete
                      </Button>
                    </DeleteOwnerAlertDialog>
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

export default CreateOwnerPage;
