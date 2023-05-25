package com.projector.callertracker.nativenotifier;

import java.util.Vector;

public class NotifierFactoryApp {
    
    private Vector<EventNotifierApp> _eventNotifiers = null;
    
    private static NotifierFactoryApp _notifierFactoryInstance = null;
    
    public static final int EVENT_NOTIFIER_LOCATION_UPDATE = 2;

    public static final int EVENT_NOTIFIER_USER_INFO = 3;
    public static final int EVENT_NOTIFIER_DOWNLOAD_INFO = 4;

    public static final int EVENT_NOTIFIER_AD_STATUS = 4;

    public static final int EVENT_NOTIFIER_DATA_UPDATED = 5;

    public static final int EVENT_NOTIFIER_DATA_LIKE = 6;


    private NotifierFactoryApp( ) {
        _eventNotifiers = new Vector<EventNotifierApp>( );
    }
    
    public static NotifierFactoryApp getInstance() {
        if ( _notifierFactoryInstance == null ) {
            _notifierFactoryInstance = new NotifierFactoryApp( );
        }
        return _notifierFactoryInstance;
    }
    
    public EventNotifierApp getNotifier(int eventCategory ) {
        
        EventNotifierApp eventNotifier = findNotifier( eventCategory );
        if ( eventNotifier != null ) {
            
            return eventNotifier;
        }
        

        EventNotifierApp objEventNotifier = new EventNotifierApp( eventCategory );
        _eventNotifiers.addElement( objEventNotifier );
        
        return objEventNotifier;
    }
    
    private EventNotifierApp findNotifier(int eventCategory ) {
        
        EventNotifierApp eventNotifierObject = null;
        
        int length = _eventNotifiers.size( );
        
        for ( int index = 0; index < length; index++ ) {
            
            eventNotifierObject = (EventNotifierApp) _eventNotifiers.elementAt( index );
            int category = eventNotifierObject.getEventCategory( );
            
            if ( eventCategory == category ) {
                
                return eventNotifierObject;
            }
            eventNotifierObject = null;
        }
        return eventNotifierObject;
    }
}