package com.example.marvelguide;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MarvelDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "marvel"; //the name of our database
    private static final int DB_VERSION = 1; //the version of the database

    public MarvelDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d("","****MarvelDatabaseHelper onCreate****");
        updateMyDatabase(db, 0, DB_VERSION);
        tableToString(db,"MOVIE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.d("","****MarvelDatabaseHelper onUpgrade****");
        db.execSQL("DROP TABLE IF EXISTS MOVIE");
        updateMyDatabase(db, oldVersion, newVersion);
        tableToString(db,"MOVIE");
    }

    private static void insertDb(SQLiteDatabase db, String name, String description, String cast, int year, int phase, int chronological, int resourceId){
        ContentValues dbValues = new ContentValues();
        dbValues.put("NAME", name);
        dbValues.put("DESCRIPTION", description);
        dbValues.put("_CAST", cast);
        dbValues.put("YEAR", year);
        dbValues.put("CHRONOLOGICAL", chronological);
        dbValues.put("PHASE", phase);
        dbValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("MOVIE", null, dbValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE MOVIE ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "_CAST TEXT, "
                    + "YEAR INTEGER, "
                    + "CHRONOLOGICAL INTEGER, "
                    + "PHASE INTEGER, "
                    + "IMAGE_RESOURCE_ID INTEGER,"
                    + "WATCHED NUMERIC,"
                    + "DATE_WATCHED TEXT);");

            //insert movies
            // phase 1
            insertDb(db, "Captain America: The First Avenger","It takes place during World War 2, in 1942 to be exact, its setting is certainly the oldest. Travel back in time to watch Cap fight HYDRA masquerading as Nazis and fall for Peggy Carter, only to be frozen in ice so he could conveniently leapfrog through time to the present day and eventually team up with the Avengers. This is where it all began...","Chris Evans, Hayley Atwell, Hugo Weaving",2011,1,1, R.drawable.captain_america_the_first_avenger);
            insertDb(db, "Iron Man", "That brings us to Iron Man, the very first Marvel movie made, but the third in chronological order. Set in 2010, this origin movie started it all for the Marvel Cinematic Universe as Tony Stark creates the Iron Man suit to escape his captors and then become a powerful but arrogant superhero. With his P.A. Pepper Potts and friend Col. James “Rhodey” Rhodes along for the ride, Tony eventually takes down baddie Obadiah Stane who arranged Tony’s kidnapping so he could take over Stark Industries.","Robert Downey Jr., Gwyneth Paltrow, Terrence Howard", 2008, 1, 3, R.drawable.ironman);
            insertDb(db, "Iron Man 2","Less than a year later Tony is back in hot water as the son of a former Stark Industries employee, Ivan Vanko, tries to kill him using his own version of Tony’s Iron Man suit technology. And, as if that wasn’t bad enough, Tony finds out that the palladium core in the Arc Reactor that keeps him alive and powers his suit is actually killing him. All in all, Tony isn’t having a great year, but thanks to a little help from Nick Fury and Natasha Romanoff, aka S.H.I.E.L.D. agent Black Widow, he eventually cures himself and takes down Vanko.","Robert Downey Jr., Mickey Rourke, Gwyneth Paltrow",2010,1,4, R.drawable.ironman_2);
            insertDb(db, "The Incredible Hulk","At the same time, Bruce Banner is trying to find a cure for his condition which sees him turn into a big, raging, green monster anytime he feels a bit irritated. Set in 2011, Banner is being hunted by General Thaddeus “Thunderbolt” Ross who sends special forces soldier Emil Blonsky to take him down. Unfortunately, Blonsky is no match for the Hulk which is why he agrees to be injected with a similar serum to the one that turned Banner into the Hulk. The problem is, it also makes him insane. Eventually becoming Abomination and going on a killing rampage, Banner convinces Ross to let him stop Blonsky and save the day.","Edward Norton, Liv Tyler, Tim Roth",2008,1,5, R.drawable.the_incredible_hulk);
            insertDb(db, "Thor","Also at the same time (yes, seriously, 2011 was a busy year for the MCU), God of Thunder Thor is banished to Earth by his dad Odin and discovers he’s no longer worthy enough to lift his favorite hammer. There he meets scientist Jane Foster and learns how to live as a mortal for a while. Meanwhile on Asgard, Thor’s brother Loki, the God of Mischief, finds out he’s actually adopted and doesn’t take it particularly well. Eventually, Thor proves himself worthy enough to once again wield his hammer and returns to Asgard to stop Loki from causing anymore trouble, only to witness his brother supposedly die.","Chris Hemsworth, Anthony Hopkins, Natalie Portman",2011,1,6, R.drawable.thor);
            insertDb(db, "The Avengers","Surprise! A year after Loki’s ‘death’ and he’s back trying to take over Earth, which leads to the creation of the Avengers in 2012. A recently unfrozen Cap, Iron Man (who’s trying to be slightly more of a team player), a reluctant Banner/Hulk, and Thor (who’s traveled to Earth to stop his brother) all team up to stop Loki. They’re also joined by Black Widow and her pal Clint Barton, aka Hawkeye. Long story short, a huge battle ensues destroying most of New York, but eventually ends with Loki being captured and taken back to Asgard for a spanking.","Robert Downey Jr., Chris Evans, Scarlett Johansson",2012,1,7, R.drawable.the_avengers);
            // phase 2
            insertDb(db, "Thor: The Dark World","Fresh off the back of taking down his brother, Thor returns to Earth and runs into Jane who has accidentally absorbed a dangerous substance called the Aether, and is more than a little upset Thor didn’t call her when he was in town. After discovering that the Aether will kill Jane, Thor enlists the help of his wayward brother to trick dark elf Malekith into drawing the Aether out of Jane. Loki is, once again, supposedly killed during the confrontation and Malekith tries to use the Aether to wreak havoc on Earth, but is stopped by Thor and Jane.","Chris Hemsworth, Natalie Portman, Tom Hiddleston",2013,2,8, R.drawable.thor_the_dark_world);
            insertDb(db, "Iron Man 3","While this is all happening, Tony is struggling given his experiences during the Battle of New York. Convinced that the only way to protect Earth and the ones he loves is with more Iron Man suits, he spends the better half of 2012 creating suit after suit after suit, only for bombs to start going off all over the place, allegedly orchestrated by the Mandarin. During his investigation into the Mandarin, Tony discovers that scientist Aldrich Killian is really behind the attacks and that he’s kidnapped the President. Tony teams up with Rhodey to save the President, eventually destroying all his suits and promising Pepper he’ll tone down the superhero thing for a while.","Robert Downey Jr., Guy Pearce, Gwyneth Paltrow",2013,2,9, R.drawable.iron_man_3);
            insertDb(db, "Captain America: The Winter Soldier","After the events of Avengers Assemble, Captain America is spending 2014 continuing to adjust to present-day life when an assassin called the Winter Soldier starts causing trouble for S.H.I.E.L.D. It turns out that the Winter Soldier is actually Cap’s old friend Bucky from 1942 who’s been brainwashed and periodically kept on ice by HYDRA. Oh, and HYDRA has taken over S.H.I.E.L.D. as well. Cap, along with Black Widow and his new friend Sam Wilson, aka Falcon, help bring HYDRA/S.H.I.E.L.D. down, but Bucky ends up on the run.","Chris Evans, Sebastian Stan, Scarlett Johansson",2014,2,10, R.drawable.captain_america_the_winter_soldier);
            insertDb(db, "Guardians of the Galaxy","That same year Star-Lord is roaming the galaxy trying to find a buyer for a powerful Infinity Stone he’s stolen when he gets ambushed by Thanos’s adopted daughter, Gamora, and bounty hunters, Rocket and Groot, who are also all after the stone. After being sent to prison and meeting Drax, the unlikely friends team up, break out, and decide to sell the stone to keep it from baddie Ronan, who wants to use it to destroy Xandar. All doesn’t quite go to plan and they end up in a big battle with Ronan, which ends with an awkward dance off and victory for the Guardians of the Galaxy.","Chris Pratt, Zoe Saldana, Lee Pace",2014,2,11, R.drawable.guardians_of_the_galaxy);
            insertDb(db, "Avengers: Age of Ultron","the Avengers having to reform to defeat another villain, except this time it was one of their own making. Well, Tony’s anyway. Still desperately trying to protect humanity, he creates an A.I. robot called Ultron who’s more interested in enslaving humanity than saving it. The heroes have to work together – along with some new friends in the form of Quicksilver and Scarlet Witch – to stop Ultron, but it comes at a high price, with the destruction of Sokovia.","Robert Downey Jr., Chris Evans, Chris Hemsworth",2015,2,13, R.drawable.avengers_age_of_ultron);
            insertDb(db, "Ant-Man","While the Avengers were taking down Ultron, ex-con Scott Lang was breaking into Hank Pym’s house and accidentally stealing his Ant-Man suit. After getting caught, Hank reveals he set up the whole thing to see if Scott was good enough to become the next Ant-Man. The good news is he passed, the bad news is that Hank needs his help to stop his former protégé and all round jerk, Darren Cross, from developing his own suit. After training with Hank and his daughter Hope, Scott finally gets the shrinking thing down and saves the day.","Paul Rudd, Michael Douglas, Evangeline Lilly",2015,2,14, R.drawable.ant_man);
            // phase 3
            insertDb(db, "Captain Marvel","Next you’ll jump forward in time to the 90s where Captain Marvel discovers grunge as well as a community of Skrulls hiding in plain sight on Earth. Set in 1995, Marvel gave us a strong blast of nostalgia with this origin movie, which saw appearances from 90s icons such as Blockbuster, payphones, and Nine Inch Nails. Plus, it was the first Marvel movie to have one of its main characters de-aged using some impressive CGI so a young Nick Fury could kick ass right alongside one of the most powerful superheroes in the franchise.","Brie Larson, Samuel L. Jackson, Jude Law",2019,3,2, R.drawable.captain_marvel);
            insertDb(db, "Guardians of the Galaxy Vol. 2","No sooner had the Guardians of the Galaxy defeated Ronan, then they were faced with another big baddie in the same year, this time with some added family drama. Star-Lord’s estranged, but very powerful daddy, Ego, turns up and announces he wants to teach Star-Lord his skills. But, before they can get very far with the lessons, it turns out that Ego is a mass-murdering psychopath and the Guardians have to destroy him, which isn’t as easy as it sounds. At least they made a new friend in the form of empath Mantis.","Chris Pratt, Zoe Saldana, Kurt Russell",2017,3,12, R.drawable.guardians_of_the_galaxy_vol_2);
            insertDb(db, "Captain America: Civil War","After the events of Avengers: Age of Ultron, the U.N. asks the Avengers to sign the Sokovia Accords and promise not to do any superhero-ing until they’re told to. Unfortunately, only half the Avengers think it’s a good idea, with Iron Man and Captain America on opposing sides. To make matters worse, King T’Chaka is killed, supposedly by Cap’s old buddy Bucky/the Winter Soldier, causing an international incident involving Black Panther. The Avengers end up having a big bust up, but Cap and Bucky track down the real murderer, Zemo, and discover that he coordinated the whole thing to split the Avengers up. That’s when Zemo reveals Bucky killed Iron Man’s parents, and Cap and Tony’s friendship is officially over.","Chris Evans, Robert Downey Jr., Scarlett Johansson",2016,3,15, R.drawable.captain_america_civil_war);
            insertDb(db, "Spider-Man: Homecoming","After teaming up with Iron Man in Captain America: Civil War, Peter Parker/Spider-Man is back in his old neighborhood when he discovers that weapons made from alien tech are being sold by Adrian Toomes. He tries to stop him, but has to be saved by Iron Man, who eventually confiscates the suit he made for Peter when he thinks he’s become too reckless. Trying to get back to normal life, Peter asks his crush, Liz, to the prom, only to discover that her dad is Toomes. Awkward. Realizing Toomes is going to try and steal a plane full of alien weapons, Peter abandons his prom date to stop him.","Tom Holland, Michael Keaton, Robert Downey Jr.",2017,3,17, R.drawable.spider_man_homecoming);
            insertDb(db, "Black Panther","After the death of his father in Captain America: Civil War, T’Challa returns to Wakanda to take his rightful place as king, but quickly discovers that Wakandan artifacts are being stolen. In his attempt to apprehend the man responsible, black market arms dealer Ulysses Klaue, C.I.A. agent Everett K. Ross is badly hurt. Meanwhile, Klaue is double-crossed by his accomplice Erik Stevens/Killmonger who kills him, brings his body to Wakanda, and reveals himself to be T’Challa’s cousin. He challenges T’Challa to ritual combat for the throne and wins. Planning to send Wakanda weapons to his operatives around the world, T’Challa must gather his remaining allies to stop him and become king once again.","Chadwick Boseman, Michael B. Jordan, Lupita Nyong’o",2018,3,18, R.drawable.black_panther);
            insertDb(db, "Doctor Strange","Brilliant but arrogant surgeon Dr. Stephen Strange is in a car accident that destroys his hands. Devastated that he can no longer wield a scalpel, he travels the world attempting to find a cure and eventually meets The Ancient One. After agreeing to train him in the mystic arts, Strange is learning how to become a sorcerer when a former pupil, Kaecilius, attacks looking for magic that will help him contact Dormammu of the Dark Dimension. Killing The Ancient One, Kaecilius succeeds in contacting Dormammu, and Strange creates a time loop prison for Dormammu until he agrees to leave Earth forever.","Benedict Cumberbatch, Chiwetel Ejiofor, Rachel McAdams",2016,3,19, R.drawable.doctor_strange);
            insertDb(db, "Thor: Ragnarok","Thor returns to Asgard to discover his brother Loki is alive and impersonating their father, Odin. They find their dad, only to discover he’s dying and that they apparently have a terrible older sister, Hela, who’s only kept caged by Odin’s magic. Once he dies Hela appears and takes over Asgard, promising to wage war on the rest of the universe. Thor and Loki escape to Sakaar and are reunited with the Hulk (who’s been missing since Age of Ultron) and make friends with Valkyrie. They band together to return to Asgard and defeat Hela by bringing about Ragnarok/the apocalypse, escaping with most of Asgard’s people on a spaceship.","Chris Hemsworth, Tom Hiddleston, Cate Blanchett",2017,3,20, R.drawable.thor_ragnarok);
            insertDb(db, "Ant-Man and the Wasp","At the same time, Scott/Ant-Man is under house arrest thanks to his involvement in Captain America: Civil War when he runs into Hope and Hank who are on the run because the authorities think they also had something to do with it. They need his help to rescue Hope’s mom Janet from the Quantum Realm, but they’re not the only ones trying to master the Quantum Realm. Ghost tries to steal Hank’s lab, but Ant-Man and the Wasp team up to stop her while Hank successfully brings Janet back from the Quantum Realm. Everything works out fine, until everyone mysteriously disintegrates, except for Ant-Man, who’s trapped in the Quantum Realm.","Paul Rudd, Evangeline Lilly, Michael Peña",2018,3,21, R.drawable.ant_man_and_the_wasp);
            insertDb(db, "Avengers: Infinity War","Thanos, who’s already in possession of the Power Stone, destroys the Asgardian ship, killing Loki (for real this time), to get the Space Stone. He then sends his adopted children to Earth in search of the Mind and Time Stones, as he goes off to find the Reality and Soul Stones. The Avengers, the Guardians of the Galaxy, and almost every other superhero in the MCU try their hardest to stop Thanos from getting the Infinity Stones, but one by one he claims them all and the movie climaxes with him snapping his figures to whipping out half of the universe. Bummer.","Robert Downey Jr., Chris Hemsworth, Chris Evans",2018,3,22, R.drawable.avengers_infinity_war);
            insertDb(db, "Avengers: Endgame","Five years later and the world is still trying to readjust to life after losing so much when Ant-Man returns from the Quantum Realm with an idea to bring everyone back. Using his knowledge, Tony and smart Hulk find a way to time travel and they, along with the remaining heroes, go back to different points in time to find the Infinity Stones before Thanos. When they return (minus Black Widow who’s killed, R.I.P), they use the stones to bring everyone back – but Thanos is back too and ALL the Avengers must assemble to stop him. Iron Man saves the day with a click of his fingers, but dies in the attempt.","Robert Downey Jr., Chris Hemsworth, Chris Evans",2019,4,23, R.drawable.avengers_endgame);
            insertDb(db, "Spider-Man: Far From Home","The following year, Peter is still grieving Tony’s death when he goes on a school trip to Europe just as it’s attacked by Elemental monsters. Nick Fury appears and gives him Tony’s old glasses that have access to all of Stark Industries resources, and Peter teams up with Beck, a superhero from across the multiverse, to defeat the Elementals. Believing Beck is the hero the world needs, Peter gives him Tony’s glasses, only to discover that he’s not a superhero at all but an ex-Stark employee who was after the glasses all along. Peter and his friends eventually stop Beck, but not before he lands Spider-Man in hot water.","Tom Holland, Samuel L. Jackson, Jake Gyllenhaal",2019,4,24, R.drawable.spider_man_far_from_home);
            // phase 4
            insertDb(db, "Black Widow","Later that same year, Natasha is on the run for breaking the Sokovia Accords when she finds out that the Red Room organization that tortured and trained her to be a Black Widow is still up and running. With an antidote to the Black Widow mind-control, and her former fake family in tow, Natasha tracks down the head of the Red Room, Dreykov who’s turned his own daughter into the perfect soldier, Taskmaster. Natasha and co use the antidote to save her and the other Black Widows and make plans to rescue the rest, all around the world.","Scarlett Johansson, Florence Pugh, David Harbour",2021,4,16, R.drawable.black_widow);
            insertDb(db, "Shang-Chi and the Legend of the Ten Rings","During these events, Shaun/Shang-Chi is in San Francisco when he’s attacked by his dad’s criminal organization, the Ten Rings. He goes to find his sister, Xialing, but the Ten Rings also show up there and take both of them to their dad, who reveals he’s going to find their dead mother in a place called Ta Lo. Once in Ta Lo, their aunt tells them that their dad is being manipulated by the Dweller-in-Darkness monster into setting him free, and they have to work together to try and stop him.","Simu Liu, Awkwafina, Tony Chiu-Wai Leung",2021,4,25, R.drawable.shang_chi);
            insertDb(db, "Eternals","It turns out that a race of immortals called the Eternals have been living on Earth in secret since the dawn of time, protecting humanity from the divergents. They think that’s their only job but actually, they’re just supposed to make sure humanity develops enough so that a giant Celestial can be born from the Earth’s core.","Gemma Chan, Richard Madden, Angelina Jolie",2021,4,26, R.drawable.eternals);
            insertDb(db, "Spider-Man: No Way Home","After Mysterio revealed Spider-Man’s ‘secret identity’ and blamed him for the attacks in London, Peter’s life has gone from bad to worse. Some people think he’s a murderer, the press won’t leave him alone and he and his friend can’t get into college.","Tom Holland, Zendaya, Benedict Cumberbatch",2021,4,27, R.drawable.spider_man_no_way_home);
            insertDb(db, "Doctor Strange in the Multiverse of Madness","Dr Stephen Strange casts a forbidden spell that opens a portal to the multiverse. However, a threat emerges that may be too big for his team to handle.","Benedict Cumberbatch, Elizabeth Olsen, Chiwetel Ejiofor, Benedict Wong, Xochitl Gomez",2022,4,28, R.drawable.doctor_strange_in_the_multiverse_of_madness);

        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE MOVIE ADD COLUMN FAVORITE NUMERIC");
        }
    }

    public String tableToString(SQLiteDatabase db, String tableName) {
        Log.d("MyActivity","tableToString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM MOVIE", null);
        tableString += cursorToString(allRows);
        return tableString;
    }

    @SuppressLint("Range")
    public String cursorToString(Cursor cursor){
        String cursorString = "";
        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();
            for (String name: columnNames)
                cursorString += String.format("%s ][ ", name);
            cursorString += "\n";
            do {
                for (String name: columnNames) {
                    cursorString += String.format("%s ][ ",
                            cursor.getString(cursor.getColumnIndex(name)));
                }
                cursorString += "\n";
            } while (cursor.moveToNext());
        }
        return cursorString;
    }
}
