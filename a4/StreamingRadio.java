package cs445.a4;

/**
 * This abstract data type represents the backend for a streaming radio service.
 * It stores the songs, stations, and users in the system, as well as the
 * ratings that users assign to songs.
 */
public interface StreamingRadio {
    /**
     * The abstract methods below are declared as void methods with no
     * parameters. You need to expand each declaration to specify a return type
     * and parameters, as necessary. You also need to include a detailed comment
     * for each abstract method describing its effect, its return value, any
     * corner cases that the client may need to consider, any exceptions the
     * method may throw (including a description of the circumstances under
     * which this will happen), and so on. You should include enough details
     * that a client could use this data structure without ever being surprised
     * or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a new song to the system.
     *
     * <p> If the song is not null, this set does not contain the song, and system
     * has available capacity (if applicable), then addSong modifies the System so
     * that it contains the song. All other entries remain unmodified.
     *
     * <p> If the song is null, then addSong throws NullPointerException without
     * modifying the system. If this system already contains the song, then addSong
     * returns false without modifying the system. If this system has a capacity
     * limit, and does not have available capacity, then addSong throws
     * SystemFullException without modifying the system. If this system does not have a
     * capacity limit (i.e., if it resizes as needed), then it will never throw
     * SystemFullException.
     * @param  theSong song that should be added
     * @return True if Song successfully added; false if theSong already in the System
     * @throws SystemFullException If the System is full
     * @throws NullPointerException If theSong is Null
     */
    public boolean addSong(Song theSong)
    throws SystemFullException,NullPointerException;


    /**
     * Removes a specific and existing song from the System, if possible.
     *
     * <p> If this system contains the song, removeSong modifies the system so that it no
     * longer contains the song. All other entries remain unmodified. The removed song
     * will be returned.
     *
     * <p> If this system does not contain the song, removeSong will return null without
     * modifying the system. Because null cannot be added, a return value of null
     * will never indicate a successful removal.
     *
     * <p> If the specified song is null, then removeSong throws
     * NullPointerException without modifying the system.
     * Or if the system is empty, then removeSong throws SystemEmptyException
     * Without modifying the system.
     *
     * @param  theSong The Song to be removed
     * @return The removed song if removal was successful; null otherwise
     * @throws NullPointerException  If theSong is null
     * @throws SystemEmptyException If the System is empty
     */
    public Song removeSong(Song theSong) 
    throws SystemEmptyException,NullPointerException;


     /**
     * Adds an existing song from the system to the playlist for an existing radio station.
     * Assuming playlist is a property of radio station
     *
     * <p> If the song is not null, the station does not contain the song, and the song
     * exists within the system then addToStation modifies the station so
     * that it contains the song. All other entries remain unmodified.
     *
     * <p> If theSong is null, then addToStation throws NullPointerException without
     * modifying the system or station.
     * If theStation is null, then addToStation throws NullPointerException without
     * modiying the system or station.
     *
     * <p> If this station already contains theSong, then addToStation
     * returns false without modifying the system. If this station has a capacity
     * limit, and does not have available capacity, then addToStation throws
     * StationFullException without modifying the station. If this station does not have a
     * capacity limit (i.e., if it resizes as needed), then it will never throw
     * StationFullException.
     *
     * <p> If the System is empty (song cannot be added/does not exist) then addToStation throws
     * SystemEmptyException without modifying the station
     *
     *@param  theStation The station that the song should be added to
     *@param  theSong The song that should be added to the station
     *@return True if Song successfully added to station; false if theSong already in the Station
     *@throws SystemEmptyException If the System is empty
     *@throws StationFullException If the Station is full
     *@throws NullPointerException If theSong is Null or theStation is Null
     */
    public boolean addToStation(Station theStation, Song theSong) 
    throws SystemEmptyException,StationFullException,NullPointerException;

    /**
     * Removes a specific and existing song from the playlist for a radio station.
     *
     * <p> If this station contains the song, remove modifies the station so that it no
     * longer contains the song. All other entries remain unmodified. The removed song
     * will be returned.
     *
     * <p> If this station does not contain the song, remove will return null without
     * modifying the station. Because null cannot be added, a return value of null
     * will never indicate a successful removal.
     *
     * <p> All entries in the station will be a song from the system, because addToStation does not permit
     * Any non-songs or songs not in the system already to be added. 
     *
     * <p> If the specified song is null, then remove throws
     * NullPointerException without modifying the station.
     *
     * <p> If the specified station is null, then remove throws
     * NullPointerException without modifying the station.
     *
     * <p> If the station is empty (song cannot be removed/does not exist within the station) then removeFromStation throws
     * StationEmptyException without modifying the station.
     *
     *
     * @param  theStation The station that the song should be removed from
     * @param  theSong The song to be removed from the station
     * @return The removed song if removal was successful; null otherwise
     * @throws NullPointerException  If theSong or theStation is null
     * @throws StationEmptyException If the station is empty
     */
    public Song removeFromStation(Station theStation, Song theSong) 
    throws NullPointerException,StationEmptyException;

    /**
     * Sets a user's rating for a song if song exists in the system, as a number of stars from 1 to 5 inclusive.
     * Will override any existing rating on the song
     * The song will still be rated if it has never been rated before (Rating of 0) by the user
     *
     * <p> If the user is not null, song exists within the system, and the rating is between 1 and 5 then
     * rateSong sets the song rating to the rating given, regardless if there was an existing rating for
     * the song prior
     *
     * <p> If the specified user is null or the specified song is null or does not exist within the system, then rateSong throws
     * NullPointerException without modifying anything else
     *
     *
     * @param  theUser The user for which the song's rating should be set
     * @param  theSong The song for which the rating belonging to the user should be set
     * @param  rating The integer rating for which the user's rating for a song should be
     * @throws IllegalArgumentException If rating is less than 1 or greater than 5
     * @throws NullPointerException If theUser or theSong is null, or theSong does not exist in the system
     */
    public void rateSong(User theUser, Song theSong, int rating)
    throws IllegalArgumentException,NullPointerException;

    /**
     * Clears a user's rating on a song. If this user has rated this song and
     * the rating has not already been cleared, then the rating is cleared and
     * the state will appear as if the rating was never made. If there is no
     * such rating on record (either because this user has not rated this song,
     * or because the rating has already been cleared), then this method will
     * throw an IllegalArgumentException.
     *
     * @param  theUser user whose rating should be cleared
     * @param  theSong song from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a
     * rating on record for the song
     * @throws NullPointerException if either the user or the song is null
     */
    public void clearRating(User theUser, Song theSong)
    throws IllegalArgumentException, NullPointerException;


    /**
     * Predicts the rating a user will assign to a song that they have not yet
     * rated, as a number of stars from 1 to 5 inclusive
     * 
     * <p> If the user is not null and the song exists within the system and the song has not been rated yet by the user then
     * predictRating returns a integer value from 1 to 5 inclusive that is the predicted rating the user will give the song
     *
     * <p> If the specified user is null or the specified song is null, then predictRating throws
     * NullPointerException without modifying anything else
     * If the song has already been assigned a rating by the user, then predictRating throws
     * AlreadyHasRatingException without modifying anything else
     *
     * <p> If the song does not exist within the system then predictRating will throw NullPointerException
     *
     * @param  theUser The user for which we should predict the rating they will assign to the specified song
     * @param  theSong The song for which the rating should be predicted
     * @return The integer rating predicted value from 1 through 5 inclusive
     * @throws NullPointerException If theUser or theSong is null, or theSong does not exist in the system
     * @throws AlreadyHasRatingException If the song has already been assigned a rating by the user
     */
    public int predictRating(User theUser, Song theSong)
    throws NullPointerException,AlreadyHasRatingException;

    /**
     * Suggests a song for a user that they are predicted to like.
     *
     * <p> If the user is not null then suggestSong returns a Song that exists within the system that the User is predicted to like
     *
     * <p> If the user is null then suggestSong throws NullPointerException without modifying anything else
     *
     * @param theUser The user for which to suggest a song for
     * @return A Song that exists within the system that the User is predicted to like
     * @throws NullPointerException If theUser is null
     */
    public Song suggestSong(User theUser);

}