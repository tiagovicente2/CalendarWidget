package com.calendar.widget.ui.overlay;

/**
 * RecyclerView adapter for displaying calendar events grouped by day.
 * Supports both day headers and event items with proper Material styling.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u0000 \u00172\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0005\u0017\u0018\u0019\u001a\u001bB\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\tH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0018\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0006H\u0016J\u0014\u0010\u0016\u001a\u00020\u00102\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\t\u00a8\u0006\u001c"}, d2 = {"Lcom/calendar/widget/ui/overlay/EventAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/calendar/widget/ui/overlay/EventAdapter$ListItem;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "()V", "getItemViewType", "", "position", "groupEventsByDay", "", "events", "Lcom/calendar/widget/data/model/Event;", "hasEventsBelow", "", "headerPosition", "onBindViewHolder", "", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "submitEvents", "Companion", "DiffCallback", "EventViewHolder", "HeaderViewHolder", "ListItem", "app_debug"})
public final class EventAdapter extends androidx.recyclerview.widget.ListAdapter<com.calendar.widget.ui.overlay.EventAdapter.ListItem, androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_EVENT = 1;
    @org.jetbrains.annotations.NotNull()
    public static final com.calendar.widget.ui.overlay.EventAdapter.Companion Companion = null;
    
    @javax.inject.Inject()
    public EventAdapter() {
        super(null);
    }
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
    }
    
    private final boolean hasEventsBelow(int headerPosition) {
        return false;
    }
    
    /**
     * Submits a list of events, automatically grouping them by day with headers.
     */
    public final void submitEvents(@org.jetbrains.annotations.NotNull()
    java.util.List<com.calendar.widget.data.model.Event> events) {
    }
    
    private final java.util.List<com.calendar.widget.ui.overlay.EventAdapter.ListItem> groupEventsByDay(java.util.List<com.calendar.widget.data.model.Event> events) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/calendar/widget/ui/overlay/EventAdapter$Companion;", "", "()V", "VIEW_TYPE_EVENT", "", "VIEW_TYPE_HEADER", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/calendar/widget/ui/overlay/EventAdapter$DiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/calendar/widget/ui/overlay/EventAdapter$ListItem;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    public static final class DiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.calendar.widget.ui.overlay.EventAdapter.ListItem> {
        
        public DiffCallback() {
            super();
        }
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.calendar.widget.ui.overlay.EventAdapter.ListItem oldItem, @org.jetbrains.annotations.NotNull()
        com.calendar.widget.ui.overlay.EventAdapter.ListItem newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.calendar.widget.ui.overlay.EventAdapter.ListItem oldItem, @org.jetbrains.annotations.NotNull()
        com.calendar.widget.ui.overlay.EventAdapter.ListItem newItem) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/calendar/widget/ui/overlay/EventAdapter$EventViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Lcom/calendar/widget/ui/overlay/EventItemView;", "(Lcom/calendar/widget/ui/overlay/EventItemView;)V", "bind", "", "event", "Lcom/calendar/widget/data/model/Event;", "app_debug"})
    public static final class EventViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.calendar.widget.ui.overlay.EventItemView view = null;
        
        public EventViewHolder(@org.jetbrains.annotations.NotNull()
        com.calendar.widget.ui.overlay.EventItemView view) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.calendar.widget.data.model.Event event) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/calendar/widget/ui/overlay/EventAdapter$HeaderViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Lcom/calendar/widget/ui/overlay/DayHeaderView;", "(Lcom/calendar/widget/ui/overlay/DayHeaderView;)V", "bind", "", "date", "Ljava/util/Date;", "hasEvents", "", "app_debug"})
    public static final class HeaderViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.calendar.widget.ui.overlay.DayHeaderView view = null;
        
        public HeaderViewHolder(@org.jetbrains.annotations.NotNull()
        com.calendar.widget.ui.overlay.DayHeaderView view) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        java.util.Date date, boolean hasEvents) {
        }
    }
    
    /**
     * Sealed class representing items in the list.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/calendar/widget/ui/overlay/EventAdapter$ListItem;", "", "()V", "DayHeader", "EventItem", "Lcom/calendar/widget/ui/overlay/EventAdapter$ListItem$DayHeader;", "Lcom/calendar/widget/ui/overlay/EventAdapter$ListItem$EventItem;", "app_debug"})
    public static abstract class ListItem {
        
        private ListItem() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/calendar/widget/ui/overlay/EventAdapter$ListItem$DayHeader;", "Lcom/calendar/widget/ui/overlay/EventAdapter$ListItem;", "date", "Ljava/util/Date;", "(Ljava/util/Date;)V", "getDate", "()Ljava/util/Date;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class DayHeader extends com.calendar.widget.ui.overlay.EventAdapter.ListItem {
            @org.jetbrains.annotations.NotNull()
            private final java.util.Date date = null;
            
            public DayHeader(@org.jetbrains.annotations.NotNull()
            java.util.Date date) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Date getDate() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Date component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.calendar.widget.ui.overlay.EventAdapter.ListItem.DayHeader copy(@org.jetbrains.annotations.NotNull()
            java.util.Date date) {
                return null;
            }
            
            @java.lang.Override()
            public boolean equals(@org.jetbrains.annotations.Nullable()
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override()
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public java.lang.String toString() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/calendar/widget/ui/overlay/EventAdapter$ListItem$EventItem;", "Lcom/calendar/widget/ui/overlay/EventAdapter$ListItem;", "event", "Lcom/calendar/widget/data/model/Event;", "(Lcom/calendar/widget/data/model/Event;)V", "getEvent", "()Lcom/calendar/widget/data/model/Event;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class EventItem extends com.calendar.widget.ui.overlay.EventAdapter.ListItem {
            @org.jetbrains.annotations.NotNull()
            private final com.calendar.widget.data.model.Event event = null;
            
            public EventItem(@org.jetbrains.annotations.NotNull()
            com.calendar.widget.data.model.Event event) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.calendar.widget.data.model.Event getEvent() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.calendar.widget.data.model.Event component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.calendar.widget.ui.overlay.EventAdapter.ListItem.EventItem copy(@org.jetbrains.annotations.NotNull()
            com.calendar.widget.data.model.Event event) {
                return null;
            }
            
            @java.lang.Override()
            public boolean equals(@org.jetbrains.annotations.Nullable()
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override()
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public java.lang.String toString() {
                return null;
            }
        }
    }
}