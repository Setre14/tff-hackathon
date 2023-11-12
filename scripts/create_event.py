import os
import pandas as pd

def create_events_csv(path, out_path):
    out_path = os.path.join(out_path, "events")
    os.makedirs(out_path)

    data_path = os.path.join(path, 'TT Festival_Invenium', 'CSV Files', 'Gastein')

    combined_data = []

    name = []
    keyx = []
    dates = []
    arriving_summary = []
    departing_summary = []
    origin_summary = []
    stay_duration_avg = []
    stay_duration_min = []
    stay_duration_max = []
    nationality_summary = []
    peak_time = []

    for event_folder in os.listdir(data_path):
        event_path = os.path.join(data_path, event_folder)

        display_name = event_folder.rsplit(' ', 1)[0]
        key = display_name.lower().replace(' ', '-')

        folder_date = event_folder.rsplit(' ', 1)[1]
        date_parts = folder_date.split('_')
        day = date_parts[0]
        month = date_parts[1]
        year = date_parts[2]
        date = f'{year}-{month}-{day}'


        name.append(display_name)
        keyx.append(f'{key}-{date}')
        dates.append(date)

        for event_csv in os.listdir(event_path):
            event_csv_path = os.path.join(event_path, event_csv)

            data = pd.read_csv(event_csv_path)

            if 'Arriving and Departing Visitors' in event_csv:
                # 1. Summary of Arriving and Departing Visitors
                arriving_summary.append(data['Arriving visitors'].sum())
                departing_summary.append(data['Departing visitors'].sum())
            elif 'Site Statistics by Area of Origin' in event_csv:
                # 2. Visitor Demographics
                origin_summary.append(data[['Area of origin', 'Visitors']].groupby('Area of origin').sum())
            elif 'Stay Duration Distribution' in event_csv:
                # 3. Stay Duration Insights
                stay_duration_avg.append(data['Stay duration (minutes)'].mean())
                stay_duration_min.append(data['Stay duration (minutes)'].min())
                stay_duration_max.append(data['Stay duration (minutes)'].max())
            elif 'Top Countries of Origin' in event_csv:
                nationality_summary.append(data)
            elif 'Visitors During the Day' in event_csv:
                # 4. Visitor Trends Throughout the Day
                peak_time.append(data.loc[data['Visitors'].idxmax()]["Time"])

    # Consolidate into a final DataFrame
    summary_data = {
        '@timestamp': dates,
        'Name': name,
        'KeyX': keyx,
        'Total Arriving Visitors': arriving_summary,
        'Total Departing Visitors': departing_summary,
        'Average Stay Duration (minutes)': stay_duration_avg,
        'Min Stay Duration (minutes)': stay_duration_min,
        'Max Stay Duration (minutes)': stay_duration_max,
        'Peak Visitor Time': peak_time
    }


    final_summary_df = pd.DataFrame(summary_data)

    output_path = os.path.join(out_path, 'final-event-summary.csv')

    # Save to a new CSV file
    final_summary_df.to_csv(output_path, index=False)
