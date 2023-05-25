package com.projector.callertracker.nativenotifier;

import java.util.Vector;


public class EventNotifierApp {
    
    private Vector< ListenerObject > _registeredListener = null;
    private int _eventCategory;
    
    public EventNotifierApp(int category ) {
        
        _registeredListener = new Vector< ListenerObject >( );
        _eventCategory = category;
    }
    
    public int getEventCategory( ) {
        return _eventCategory;
    }

    public void registerListener(EventCallBack eventListener, int listenerPriority ) {
        
        try {

            if ( _checkAlreadyRegistered( eventListener, listenerPriority ) ) {
                return;
            }
            
            ListenerObject listenerObj = new ListenerObject( eventListener, listenerPriority );
            int length = _registeredListener.size( );

            if ( length == 0 ) {
                _registeredListener.addElement( listenerObj );
                return;
            }
            
            for ( int index = 0; index < length; index++ ) {
                
                ListenerObject tempObj1 = (ListenerObject) _registeredListener.elementAt( index );
                if ( listenerPriority <= tempObj1.getPriority( ) ) {
                    _registeredListener.insertElementAt( listenerObj, index );
                    return;
                }
            }

            _registeredListener.addElement( listenerObj );
            return;
        } catch ( Exception e ) {

        }
    }

    public void unRegisterListener( EventCallBack eventListener ) {
        try {
            int length = _registeredListener.size( );
            for ( int index = 0; index < length; index++ ) {
                ListenerObject listenerObj = (ListenerObject) _registeredListener.elementAt( index );
                EventCallBack iEventListener = listenerObj.getIEventListener( );

                if ( iEventListener.equals( eventListener ) ) {
                    _registeredListener.removeElementAt( index );
                    return;
                }
            }
        } catch ( Exception e ) {

            
        }
    }
    

    public int eventNotify( int eventType, Object eventObject ) {
        int eventState = EventConstant.EVENT_PROCESSED;
        try {
            int length = _registeredListener.size( );
            if ( length == 0 ) {
                return EventConstant.EVENT_IGNORED;
            }

            for ( int index = _registeredListener.size( ) - 1; index >= 0; index-- ) {
                EventCallBack listenerObj =
                        (EventCallBack) ( (ListenerObject) _registeredListener.elementAt( index ) )
                                .getIEventListener( );
                if ( listenerObj == null )
                    continue;

                eventState = listenerObj.eventNotify( eventType, eventObject );
                if ( eventState == EventConstant.EVENT_CONSUMED ) {
                    return EventConstant.EVENT_CONSUMED;
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
        return eventState;
    }
    

    private boolean _checkAlreadyRegistered(EventCallBack eventListener, int priority ) {
        try {
            int length = _registeredListener.size( );
            
            for ( int index = 0; index < length; index++ ) {
                EventCallBack listener =
                        (EventCallBack) ( (ListenerObject) _registeredListener.elementAt( index ) )
                                .getIEventListener( );
                
                if ( eventListener.equals( listener ) ) {
                    return true;
                }
            }
        } catch ( Exception e ) {
            
        }
        return false;
    }

    public class ListenerObject {
        private EventCallBack _eventListener;
        private int _priority;

        public ListenerObject(EventCallBack eventListener, int priority ) {
            _eventListener = eventListener;
            _priority = priority;
        }
        

        public EventCallBack getIEventListener( ) {
            return _eventListener;
        }

        public int getPriority( ) {
            return _priority;
        }
    }
    
}