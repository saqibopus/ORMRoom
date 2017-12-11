package com.example.emxcel.ormroom;

/**
 * Created by emxcel on 11/12/17.
 */

public class Temp {

    /*public class CustomAdapter extends BaseAdapter implements Filterable {

        private Context mContext;
        private ArrayList<Contact> mList;

        // View Type for Separators
        private static final int ITEM_VIEW_TYPE_SEPARATOR = 0;
        // View Type for Regular rows
        private static final int ITEM_VIEW_TYPE_REGULAR = 1;
        // Types of Views that need to be handled
        // -- Separators and Regular rows --
        private static final int ITEM_VIEW_TYPE_COUNT = 2;

        ContactsFilter mContactsFilter;

        public CustomAdapter(Context context, ArrayList list) {
            mContext = context;
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_TYPE_COUNT;
        }

        @Override
        public int getItemViewType(int position) {
            boolean isSeparator = mList.get(position).mIsSeparator;

            if (isSeparator) {
                return ITEM_VIEW_TYPE_SEPARATOR;
            }
            else {
                return ITEM_VIEW_TYPE_REGULAR;
            }
        }

        @Override
        public boolean isEnabled(int position) {
            return getItemViewType(position) != ITEM_VIEW_TYPE_SEPARATOR;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;

            Contact contact = mList.get(position);
            int itemViewType = getItemViewType(position);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if (itemViewType == ITEM_VIEW_TYPE_SEPARATOR) {
                    // If its a section ?
                    view = inflater.inflate(R.layout.contact_section_header, null);
                }
                else {
                    // Regular row
                    view = inflater.inflate(R.layout.contact_item, null);
                }
            }
            else {
                view = convertView;
            }


            if (itemViewType == ITEM_VIEW_TYPE_SEPARATOR) {
                // If separator

                TextView separatorView = (TextView) view.findViewById(R.id.separator);
                separatorView.setText(contact.mName);
            }
            else {
                // If regular

                // Set contact name and number
                TextView contactNameView = (TextView) view.findViewById(R.id.contact_name);
                TextView phoneNumberView = (TextView) view.findViewById(R.id.phone_number);

                contactNameView.setText( contact.mName );
                phoneNumberView.setText( contact.mNumber );
            }

            return view;
        }

        @Override
        public Filter getFilter() {
            if (mContactsFilter == null)
                mContactsFilter = new ContactsFilter();

            return mContactsFilter;
        }

        // Filter

        private class ContactsFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                // Create a FilterResults object
                FilterResults results = new FilterResults();

                // If the constraint (search string/pattern) is null
                // or its length is 0, i.e., its empty then
                // we just set the `values` property to the
                // original contacts list which contains all of them
                if (constraint == null || constraint.length() == 0) {
                    results.values = mContacts;
                    results.count = mContacts.size();
                }
                else {
                    // Some search copnstraint has been passed
                    // so let's filter accordingly
                    ArrayList<Contact> filteredContacts = new ArrayList<Contact>();

                    // We'll go through all the contacts and see
                    // if they contain the supplied string
                    for (Contact c : mContacts) {
                        if (c.mName.toUpperCase().contains( constraint.toString().toUpperCase() )) {
                            // if `contains` == true then add it
                            // to our filtered list
                            filteredContacts.add(c);
                        }
                    }

                    // Finally set the filtered values and size/count
                    results.values = filteredContacts;
                    results.count = filteredContacts.size();
                }

                // Return our FilterResults object
                return results;
            }

            @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                mList = (ArrayList<Contact>) results.values;
                notifyDataSetChanged();
            }
        }
    }*/
}
