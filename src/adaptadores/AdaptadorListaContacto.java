package adaptadores;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;


import com.tdam2013.g14.R;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Contacts.Photo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;


public class AdaptadorListaContacto extends CursorAdapter{
	
	private Context contexto;
	private LayoutInflater mInflater;
	private int mIdContact=0;
	/*
     * As a shortcut, defines constants for the
     * column indexes in the Cursor. The index is
     * 0-based and always matches the column order
     * in the projection.
     */
    // Column index of the _ID column
    private int mIdIndex = 0;
    // Column index of the LOOKUP_KEY column
    private int mLookupKeyIndex = 1;
    // Column index of the display name column
    private int mDisplayNameIndex = 2;
    /*
     * Column index of the photo data column.
     * It's PHOTO_THUMBNAIL_URI for Honeycomb and later,
     * and _ID for previous versions.
     */
    private int mPhotoDataIndex =0;
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? 3 : 0;

	public AdaptadorListaContacto(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		
		contexto=context;
		mInflater = LayoutInflater.from(context);
	}
	
	/**
	 * Defines a class that hold resource IDs of each item layout row to prevent
	 * having to look them up each time data is bound to a row.
	 */
	private class ViewHolder {
		TextView displayname;
		QuickContactBadge quickcontact;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		/*
		 * Inflates the item layout. Stores resource IDs in a in a ViewHolder
		 * class to prevent having to look them up each time bindView() is
		 * called.
		 */
		final View itemView = mInflater.inflate(
				R.layout.pantalla_principal_contactos_item, viewGroup, false);
		final ViewHolder holder = new ViewHolder();
		holder.displayname = (TextView) itemView
				.findViewById(R.id.nombreContacto);
		holder.quickcontact = (QuickContactBadge) itemView
				.findViewById(R.id.quickContactBadge);
		itemView.setTag(holder);
		return itemView;
	}
	
	@Override
   public void bindView( View view, Context context, Cursor cursor) {
		
		// The Cursor that contains contact rows
//	    Cursor mCursor;
		
	    // The index of the _ID column in the Cursor
	    int mIdColumn;
	    // The index of the LOOKUP_KEY column in the Cursor
	    int mLookupKeyColumn;
	    // A content URI for the desired contact
	    Uri mContactUri;
	    // A handle to the QuickContactBadge view
	    QuickContactBadge mBadge;
	    
	    mBadge = (QuickContactBadge) view.findViewById(R.id.quickContactBadge);
	    /*
	     * Insert code here to move to the desired cursor row
	     */
	    // Gets the _ID column index
	    mIdColumn = cursor.getColumnIndex(Contacts._ID);
	    // Gets the LOOKUP_KEY index
	    mLookupKeyColumn = cursor.getColumnIndex(Contacts.LOOKUP_KEY);
	    // Gets a content URI for the contact
	    mContactUri = Contacts.getLookupUri(
	                cursor.getLong(mIdColumn),
	                cursor.getString(mLookupKeyColumn)
	            );
	    mBadge.assignContactUri(mContactUri);
	    
	    /*
		// The column in which to find the thumbnail ID
	    int mThumbnailColumn;
//	    
//	     * The thumbnail URI, expressed as a String.
//	     * Contacts Provider stores URIs as String values.
//	     
	    String mThumbnailUri;
//	    
//	     * Gets the photo thumbnail column index if
//	     * platform version >= Honeycomb
//	    
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        mThumbnailColumn = cursor.getColumnIndex(Contacts.PHOTO_THUMBNAIL_URI);
	    // Otherwise, sets the thumbnail column to the _ID column
	    } else {
	        mThumbnailColumn = mIdColumn;
	    }
	    
	    
//	     * Assuming the current Cursor position is the contact you want,
//	     * gets the thumbnail ID
	    
	    mThumbnailUri = cursor.getString(mThumbnailColumn);
       
		
       
       */
    	final String photoData = cursor.getString(mPhotoDataIndex);
	    final ViewHolder holder = (ViewHolder) view.getTag();
       final String displayName = cursor.getString(mDisplayNameIndex);
       
       // Sets the display name in the layout
       holder.displayname.setText(cursor.getString(mDisplayNameIndex));
       
       /*
        * Generates a contact URI for the QuickContactBadge.
        */
       final Uri contactUri = Contacts.getLookupUri(cursor.getLong(mIdIndex), cursor.getString(mLookupKeyIndex));
       holder.quickcontact.assignContactUri(contactUri);
       
       /*
        * Decodes the thumbnail file to a Bitmap.
        * The method loadContactPhotoThumbnail() is defined
        * in the section "Set the Contact URI and Thumbnail"
        */
//       Bitmap thumbnailBitmap = loadContactPhotoThumbnail(photoData);
       /*
        * Sets the image in the QuickContactBadge
        * QuickContactBadge inherits from ImageView
        */
//       holder.quickcontact.setImageBitmap(thumbnailBitmap);
   }
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		getCursor().moveToPosition(position);
//		bindView(convertView, parent.getContext(), getCursor());
//		return convertView;
		return super.getView(position, convertView, parent);
	}

	/**
     * Load a contact photo thumbnail and return it as a Bitmap,
     * resizing the image to the provided image dimensions as needed.
     * @param photoData photo ID Prior to Honeycomb, the contact's _ID value.
     * For Honeycomb and later, the value of PHOTO_THUMBNAIL_URI.
     * @return A thumbnail Bitmap, sized to the provided width and height.
     * Returns null if the thumbnail is not found.
     */
    private Bitmap loadContactPhotoThumbnail(String photoData) {
    	
//    	photoData = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? "PHOTO_THUMBNAIL_URI" : "_ID";
    	
        // Creates an asset file descriptor for the thumbnail file.
        AssetFileDescriptor afd = null;
        // try-catch block for file not found
        try {
            // Creates a holder for the URI.
            Uri thumbUri;
            // If Android 3.0 or later
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                // Sets the URI from the incoming PHOTO_THUMBNAIL_URI
                thumbUri = Uri.parse(photoData);
            } else {
            // Prior to Android 3.0, constructs a photo Uri using _ID
                /*
                 * Creates a contact URI from the Contacts content URI
                 * incoming photoData (_ID)
                 */
                final Uri contactUri = Uri.withAppendedPath(
                        Contacts.CONTENT_URI, photoData);
                /*
                 * Creates a photo URI by appending the content URI of
                 * Contacts.Photo.
                 */
                thumbUri =
                        Uri.withAppendedPath(
                                contactUri, Photo.CONTENT_DIRECTORY);
            }
    
        /*
         * Retrieves an AssetFileDescriptor object for the thumbnail
         * URI
         * using ContentResolver.openAssetFileDescriptor
         */
        afd = contexto.getContentResolver().openAssetFileDescriptor(thumbUri, "r");
        /*
         * Gets a file descriptor from the asset file descriptor.
         * This object can be used across processes.
         */
        FileDescriptor fileDescriptor = afd.getFileDescriptor();
        // Decode the photo file and return the result as a Bitmap
        // If the file descriptor is valid
        if (fileDescriptor != null) {
            // Decodes the bitmap
            return BitmapFactory.decodeFileDescriptor(
                    fileDescriptor, null, null);
            }
        // If the file isn't found
        } catch (FileNotFoundException e) {
            /*
             * Handle file not found errors
             */
        }
        // In all cases, close the asset file descriptor
        finally {
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {}
            }
        }
        return null;
    }
	
}
