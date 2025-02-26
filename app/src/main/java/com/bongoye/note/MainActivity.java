package com.bongoye.note;

import android.app.*;
import android.os.*;
import java.io.*;
import android.content.pm.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;
import android.util.*;
import android.view.View.*;
import android.widget.LinearLayout.*;
import android.transition.*;
import android.view.ActionProvider.*;
import android.webkit.*;
import android.net.*;
import android.text.Html;
import android.graphics.drawable.*;
import android.content.res.*;
import android.media.*;
import android.graphics.*;
import java.nio.channels.*;
import android.icu.text.*;
import android.widget.Magnifier.*;
import android.widget.Toolbar.*;
import android.text.TextUtils;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.*;
import org.json.*;
import java.time.*;
import android.graphics.fonts.*;
import android.text.*;

public class MainActivity extends Activity 
{
	public int hold,correct=0;
	public String password;
	public File open_path;
	public String default_path;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass);
		
		hold = 0;

		default_path = "/storage/emulated/0";
		open_path = new File(default_path);

		pass p= new pass();
		password = p.getPass();

		if (password.compareTo("1234") == 0)
		{
			setContentView(R.layout.main);
			refr();
		}

		isStoragePermissionGranted();

		ActionBar b=getActionBar();
		b.setIcon(R.drawable.index);
		
		System.out.println("version 2.1");
		
	}

	//menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_menu, menu);
		return true;
	}

	//menu items
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		View v=new View(this);

		if (id == R.id.newf)
		{
			newT(v);
		}
		else if (id == R.id.save)
		{
			save(v);
		}
		else if (id == R.id.setting)
		{
			settings(v);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onContentChanged()
	{
		super.onContentChanged();
	}

	
	//backup
	void backup()
	{
		File dataf=new File(getExternalMediaDirs()[0] +"/backup.json");
		File f= new File("/data/data/com.bongoye.note/data.json");

		try
		{	
			if (!dataf.exists())
				dataf.createNewFile();
			
			BufferedReader br=new BufferedReader(new FileReader(f));
			BufferedWriter bw=new BufferedWriter(new FileWriter(dataf));
			bw.write(br.readLine());
			bw.close();
			br.close();		
		}
		catch (Exception e)
		{
			System.out.println(e);
			return;
		}	
	}

	void refr()
	{
		Context c1= getApplicationContext();
		JSONArray ja=new JSONArray();
		JSONObject jo=new JSONObject();
		File f= new File("/data/data/com.bongoye.note/data.json");
		Instant t=Instant.now();
		try
		{	
			if (!f.exists())
			{
				f.createNewFile();
				jo.put("name", "Title");
				jo.put("data", "data");
				jo.put("date" , Date.from(t));
				ja.put(jo);

				BufferedWriter bw=new BufferedWriter(new FileWriter(f));
				bw.write(ja.toString());
				bw.close();
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
			return;
		}

		LinearLayout l=findViewById(R.id.mainLinearLayout1);
		l.removeAllViews();	

		LinearLayout.LayoutParams par=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams par1=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams details=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams par2=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams par5=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams par3=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		LinearLayout.LayoutParams par4=new LinearLayout.LayoutParams(70, 40);
			
		par.setMargins(10, 1, 10, 5);
		par1.setMargins(5, 0, 5, 0);
		details.setMargins(20,0,0,0);
		par2.setMargins(0, 2, 0, 2);
		par3.setMargins(10,0,0,0);
		par4.setMargins(300, -70, 0, 0);
		
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(f));
			ja = new JSONArray(br.readLine().toString());
			br.close();

			for (int i=0;i < ja.length();i++)
			{			
				final LinearLayout lin=new LinearLayout(this);
				lin.setOrientation(lin.VERTICAL);
				lin.setLayoutParams(par2);
				lin.setBackgroundResource(R.color.grey);

				final LinearLayout title=new LinearLayout(this);
				title.setPadding(0, 15, 0, 15);
				title.setOrientation(title.VERTICAL);
				title.setGravity(Gravity.CENTER);
				title.setLayoutParams(par5);
				//title.setBackgroundColor(Color.DKGRAY);	
				
				final TextView txt=new TextView(this);
				txt.setText( ja.getJSONObject(i).getString("name"));
				txt.setLayoutParams(par3);
				txt.setTextColor(Color.WHITE);
				txt.setTextSize(18);
				//txt.setBackgroundColor(Color.DKGRAY);
				//txt.setPadding(15, 5, 0, 5);

				final TextView dtxt=new TextView(this);			
				Date dt=new Date(ja.getJSONObject(i).getString("date"));
				dtxt.setText(dt.toLocaleString());
				dtxt.setLayoutParams(details);
				dtxt.setTextColor(Color.parseColor("#aaaaaa"));
				dtxt.setTextSize(12);
				
				final ImageView img=new ImageView(this);
				img.setImageResource(R.drawable.eye);
				img.setLayoutParams(par4);
				img.setPadding(0, 0, 0, 0);
				//img.setBackgroundColor(Color.BLUE);

				final EditText edtxt=new EditText(this);
				edtxt.setText(ja.getJSONObject(i).getString("data"));		
				edtxt.setLayoutParams(par);
				edtxt.setTextSize(13);
				edtxt.setHint("Empty");
				edtxt.setHintTextColor(Color.parseColor("#aaaaaa"));

				Object b="ed" + i;	
				edtxt.setTag(b);
				edtxt.setId(i);
				dtxt.setId(i+800);
				edtxt.setTextColor(Color.WHITE);

				final int k=i;
				edtxt.setSingleLine(true);
				edtxt.setSelected(true);			
				edtxt.setEnabled(false);
				edtxt.setBackground(c1.getResources().getDrawable(c1.getResources().getIdentifier("shape2" , "drawable", c1.getPackageName())));
			
				
				lin.setOnClickListener(new OnClickListener(){
						public void onClick(View v)
						{
							if (edtxt.isEnabled())
							{
								edtxt.setSingleLine(true);
								edtxt.setEnabled(false);
								img.setImageResource(R.drawable.eye);
							}
							else
							{
								edtxt.setSingleLine(false);
								img.setImageResource(R.drawable.eyer);
								edtxt.setEnabled(true);
							}
						}
					});

				//
				lin.setOnLongClickListener(new OnLongClickListener(){
						@Override
						public boolean onLongClick(View v)
						{
							showMenu(txt, k);
							return true;
						}
					});

				title.addView(txt);
				title.addView(dtxt);
				title.addView(img);
				
				lin.addView(title);
				lin.addView(edtxt);
				
				try{
					lin.setBackground(c1.getResources().getDrawable(c1.getResources().getIdentifier("shape1", "drawable", c1.getPackageName())));
				}catch(Exception e)
				{
					System.out.println(e);
				}
				l.addView(lin);
			}


			int c=l.getChildCount();
			View[] l1=new View[c];

			for (int i=0;i < c;i++)
			{
				l1[i] = l.getChildAt(i);
			}

			l.removeAllViews();

			for (int i=c - 1;i >= 0;i--)
			{
				l.addView(l1[i]);
			}

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
		{
			System.out.println("Permission: " + permissions[0] + "was " + grantResults[0]);
			//resume tasks needing this permission
		}
	}

	public  boolean isStoragePermissionGranted()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
				== PackageManager.PERMISSION_GRANTED)
			{
				return true;
			}
			else	
			{
				this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);	
				return false;
			}
		}
		else
		{
			return true;
		}
	}

	//show menu
	private void showMenu(final View view, final int k)
	{
		PopupMenu popupMenu = new PopupMenu(this, view);//View will be an anchor for PopupMenu
		popupMenu.inflate(R.menu.tools);
		Menu menu = popupMenu.getMenu();

		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
			{
				public boolean onMenuItemClick(MenuItem p1)
				{
					int id=p1.getItemId();

					if (id == R.id.delete)
						Delet(k);
					else if (id == R.id.rename)
						showRename(view, k);
					else if (id == R.id.dupl)
					{}
					else if (id == R.id.convert)
						showConv(view, k);
					//showCopy(view);

					return true;
				}
			});
		popupMenu.show();
	} 

	public void showConv(View v, int k)
	{
		final TextView txt=(TextView)v;

		final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

		alert.setTitle("Select Folder");
		alert.setMessage("select");

		alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton)
				{
					try
					{
						File dst=new File(open_path.toString() + "/" + txt.getText().toString() + ".txt");	
						dst.createNewFile();

						JSONArray ja=new JSONArray();
						JSONObject jo=new JSONObject();
						File f= new File("/data/data/com.bongoye.note/data.json");


						BufferedReader br=new BufferedReader(new FileReader(f));
						ja = new JSONArray(br.readLine().toString());
						br.close();

						String ans=null;
						String titl=null;

						for (int i=0;i < ja.length();i++)
						{
							if (ja.getJSONObject(i).getString("name").compareTo(txt.getText().toString()) == 0)
							{
								titl = ja.getJSONObject(i).getString("name");
								ans = ja.getJSONObject(i).getString("data");
							}	
						}

						BufferedWriter bw=new BufferedWriter(new FileWriter(dst));
						bw.write(ans);
						bw.close();

					}
					catch (Exception e)
					{}
				}
			});

		alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton)
				{
				}
			});

		LinearLayout lin =new LinearLayout(this);
		LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(500, 500);
		lin.setLayoutParams(pr);
		lin.addView(selectFolder(new File(default_path), null));
		lin.setGravity(Gravity.CENTER);

		alert.setView(lin);
		alert.show();
	}

	public ScrollView selectFolder(final File f, ScrollView scr)
	{
		open_path = f;

		if (scr == null)
			scr = new ScrollView(this);
		else
			scr.removeAllViews();

		final ScrollView scr1=scr;	
		final LinearLayout linearLayout =new LinearLayout(this);

		LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		linearLayout.setLayoutParams(pr);

		try
		{
			if (f.isDirectory())
			{
				File[] fs= f.listFiles(new FilenameFilter() {		
						public boolean accept(File directory, String fileName)
						{
							File f1= new File(directory.toString() + "/" + fileName);

							if (f1.isDirectory())	
								return true;			
							return false;
						}
					});

				Arrays.sort(fs);

				linearLayout.setOrientation(LinearLayout.VERTICAL);
				LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(600, 86);
				params.topMargin = 1;

				LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(600, 600);
				scr.setLayoutParams(p);

				//folders
				if (f.toString().compareTo(default_path) != 0)
				{

					final Button but1 = new Button(this);
					but1.setText(f.getParentFile().toString());
					but1.setAllCaps(false);
					but1.setTextColor(Color.WHITE);
					but1.setGravity(Gravity.LEFT);
					but1.setBackgroundColor(Color.BLACK);
					but1.setLayoutParams(params);

					but1.setOnClickListener(new OnClickListener(){
							public void onClick(View v)
							{
								selectFolder(f.getParentFile(), scr1);
							}
						});

					linearLayout.addView(but1);
				}


				for (int i=0;i < fs.length ;i++)
				{
					final Button but = new Button(MainActivity.this);
					but.setGravity(Gravity.LEFT);
					but.setBackgroundColor(Color.parseColor("#444444"));
					but.setLayoutParams(params);

					if (fs[i].getName().startsWith("."))
						but.setTextColor(Color.LTGRAY);		
					else
						but.setTextColor(Color.WHITE);

					but.setText(fs[i].getName());
					but.setAllCaps(false);							

					final File sd=fs[i];

					but.setOnClickListener(new OnClickListener(){
							public void onClick(View v)
							{
								selectFolder(sd, scr1);
							}
						});

					linearLayout.addView(but);
				}
				scr1.addView(linearLayout);
			}

		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), "2345" + e.toString(), Toast.LENGTH_LONG).show();
		}

		return scr1;
	}

	public void showRename(View v, final int k)
	{
		TextView txt=(TextView)v;

		final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

		final EditText edtxt=new EditText(MainActivity.this);
		edtxt.setText(txt.getText());
		edtxt.setTextSize(19);

		alert.setTitle("Rename");
		alert.setMessage("");

		alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton)
				{
					rename(k, edtxt.getText().toString());
				}
			});

		alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton)
				{
					//Delet(k);
				}
			});

		alert.setView(edtxt);
		alert.show();
	}

	public void newT(View v)
	{
		View v1=new View(this);
		save(v1);
		
		ScrollView scr=findViewById(R.id.mainScrollView);
		LinearLayout l=findViewById(R.id.mainLinearLayout1);
		LinearLayout.LayoutParams par=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams par1=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		par.setMargins(0, 1, 0, 0);
		par1.setMargins(0, 0, 5, 0);

		JSONArray ja;
		JSONObject jo=new JSONObject();

		File f= new File("/data/data/com.bongoye.note/data.json");

		try
		{	
			BufferedReader br=new BufferedReader(new FileReader(f));
			ja = new JSONArray(br.readLine().toString());
			br.close();

			jo.put("name", "new Note");
			jo.put("data", "");
			jo.put("date", Date.from(Instant.now()));
			ja.put(jo);
			
			BufferedWriter bw=new BufferedWriter(new FileWriter(f));
			bw.write(ja.toString());
			bw.close();

			int i=ja.length() - 1;

			LinearLayout.LayoutParams details=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams par2=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams par5=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams par3=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			LinearLayout.LayoutParams par4=new LinearLayout.LayoutParams(70, 40);

		
			details.setMargins(20,0,0,0);
			par2.setMargins(0, 2, 0, 2);
			par3.setMargins(10,0,0,0);
			par4.setMargins(300, -70, 0, 0);
			
			final LinearLayout lin=new LinearLayout(this);
			lin.setOrientation(lin.VERTICAL);
			lin.setLayoutParams(par2);
			lin.setBackgroundResource(R.color.grey);

			final LinearLayout title=new LinearLayout(this);
			title.setPadding(0, 15, 0, 15);
			title.setOrientation(title.VERTICAL);
			title.setGravity(Gravity.CENTER);
			title.setLayoutParams(par5);
			title.setBackgroundColor(Color.DKGRAY);	

			final TextView txt=new TextView(this);
			txt.setText( ja.getJSONObject(i).getString("name"));
			txt.setLayoutParams(par3);
			txt.setTextColor(Color.WHITE);
			txt.setTextSize(18);
			//txt.setBackgroundColor(Color.DKGRAY);
			//txt.setPadding(15, 5, 0, 5);

			final TextView dtxt=new TextView(this);
			dtxt.setText(ja.getJSONObject(i).getString("date"));
			dtxt.setLayoutParams(details);
			dtxt.setTextColor(Color.parseColor("#aaaaaa"));
			dtxt.setTextSize(12);

			final ImageView img=new ImageView(this);
			img.setImageResource(R.drawable.eye);
			img.setLayoutParams(par4);
			img.setPadding(0, 0, 0, 0);
			//img.setBackgroundColor(Color.BLUE);

			final EditText edtxt=new EditText(this);
			edtxt.setText(ja.getJSONObject(i).getString("data"));
			edtxt.setLayoutParams(par);
			edtxt.setTextSize(13);
			edtxt.setHint("Empty");

			Object b="ed" + i;	
			edtxt.setTag(b);
			edtxt.setId(i);
			dtxt.setId(i+800);
			edtxt.setTextColor(Color.WHITE);

			final int k=i;
			edtxt.setSingleLine(true);
			edtxt.setSelected(true);			
			edtxt.setEnabled(false);

			lin.setOnClickListener(new OnClickListener(){
					public void onClick(View v)
					{
						if (edtxt.isEnabled())
						{
							edtxt.setSingleLine(true);
							edtxt.setEnabled(false);
							img.setImageResource(R.drawable.eye);
						}
						else
						{
							edtxt.setSingleLine(false);
							img.setImageResource(R.drawable.eyer);
							edtxt.setEnabled(true);
						}
					}
				});

			//
			lin.setOnLongClickListener(new OnLongClickListener(){
					@Override
					public boolean onLongClick(View v)
					{
						showMenu(txt, k);
						return true;
					}
				});

			title.addView(txt);
			title.addView(dtxt);
			title.addView(img);

			lin.addView(title);
			lin.addView(edtxt);

			l.addView(lin);
			
			
			refr();
			scr.fullScroll(View.FOCUS_UP);	

		}
		catch (Exception e)
		{
			System.out.println(e);
			return;
		}
	}

	public void Delet(int i)
	{
		View v= new View(this);
		save(v);
		
		JSONArray ja;
		File f= new File("/data/data/com.bongoye.note/data.json");

		try
		{	
			BufferedReader br=new BufferedReader(new FileReader(f));
			ja = new JSONArray(br.readLine().toString());
			br.close();

			ja.remove(i);

			BufferedWriter bw=new BufferedWriter(new FileWriter(f));
			bw.write(ja.toString());
			bw.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
			return;
		}
		
		refr();
	}

	public void rename(int i, String str)
	{
		View v=new View(this);
		save(v);
		JSONArray ja;
		File f= new File("/data/data/com.bongoye.note/data.json");

		try
		{	
			BufferedReader br=new BufferedReader(new FileReader(f));
			ja = new JSONArray(br.readLine().toString());
			br.close();

			ja.getJSONObject(i).put("name", str);

			BufferedWriter bw=new BufferedWriter(new FileWriter(f));
			bw.write(ja.toString());
			bw.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
			return;
		}

		refr();
	}

	public void save(View v)
	{
		JSONArray ja;
		File f= new File("/data/data/com.bongoye.note/data.json");

		try
		{	
			BufferedReader br=new BufferedReader(new FileReader(f));
			ja = new JSONArray(br.readLine().toString());
			br.close();

			for (int j=0;j < ja.length();j++)
			{
				EditText ed=findViewById(j);
				TextView txt=findViewById(j+800);
				
				ja.getJSONObject(j).put("data", ed.getText().toString());
				ja.getJSONObject(j).put("date", txt.getText().toString());
			}

			BufferedWriter bw=new BufferedWriter(new FileWriter(f));
			bw.write(ja.toString());
			bw.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
			return;
		}

	}

	@Override
	protected void onPause()
	{
		View v=new View(this);
		save(v);
		backup();
		super.onPause();
	}


	void passwordCheck(char no)
	{
		TextView ed=findViewById(R.id.passTextView);
		String s=ed.getText().toString();

		if (s.contains("w"))
		{
			ed.setText("");
		}
		ed.setText(ed.getText().toString() + no);


		if (hold == 0 && no == password.charAt(0))
		{
			correct = correct + 0;
		}
		else if (hold == 1 && no == password.charAt(1))
		{
			correct = correct + 0;
		}
		else if (hold == 2 && no == password.charAt(2))
		{
			correct = correct + 0;
		}
		else if (hold == 3 && no == password.charAt(3))
		{
			correct = correct + 0;
		}
		else
		{
			correct = correct + 1;
		}

		if (correct >= 1  && hold >= 3)
		{
			ed.setText("wrong password");
			hold = -1;
			correct = 0;
		}
		else if (hold >= 3 && correct == 0)
		{
			setContentView(R.layout.main);
			refr();
			hold = -1;
		}

		hold++;
	}

	public void btn(View v)
	{passwordCheck('1');}
	public void btn2(View v)
	{passwordCheck('2');}
	public void btn3(View v)
	{passwordCheck('3');}
	public void btn4(View v)
	{passwordCheck('4');}
	public void btn5(View v)
	{passwordCheck('5');}
	public void btn6(View v)
	{passwordCheck('6');}
	public void btn7(View v)
	{passwordCheck('7');}
	public void btn8(View v)
	{passwordCheck('8');}
	public void btn9(View v)
	{passwordCheck('9');}
	public void settings(View v)
	{setContentView(R.layout.settings);}
	public void backsettings(View v)
	{setContentView(R.layout.main);}

	public void savebutton(View v)
	{ 
		EditText ed=findViewById(R.id.mainEditText);
		if (ed.getText().length() < 4)
		{
			ed.setText("1234");
		}
		else
		{
			try
			{	
				File f= new File("/data/data/com.bongoye.note/pass.txt");
				DataOutputStream out=new DataOutputStream(new FileOutputStream(f));
				out.writeBytes(ed.getText().toString());
				out.close();
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}

	class pass
	{
		public String defaultPass="1234";

		public String getPass()
		{
			String count=defaultPass;

			try
			{	
				File f= new File("/data/data/com.bongoye.note/pass.txt");

				if (!f.exists())
				{
					DataOutputStream out=new DataOutputStream(new FileOutputStream(f));
					f.createNewFile();
					out.writeBytes(defaultPass);
					out.close();
				}

				DataInputStream d =new DataInputStream(new FileInputStream(f));		
				count = d.readLine();
				d.close();

			}
			catch (Exception e)
			{
				System.out.println(e);
			}

			return count;
		}
	}
}
