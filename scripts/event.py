import pandas as pd

# Load the datasets
arriving_departing_df = pd.read_csv('Ski Classics - 2022-12-11 (7 - 18h) - Arriving and Departing Visitors.csv')
origin_df = pd.read_csv('Ski Classics - 2022-12-11 (7 - 18h) - Site Statistics by Area of Origin.csv')
stay_duration_df = pd.read_csv('Ski Classics - 2022-12-11 (7 - 18h) - Stay Duration Distribution.csv')
top_countries_df = pd.read_csv('Ski Classics - 2022-12-11 (7 - 18h) - Top Countries of Origin.csv')
visitors_day_df = pd.read_csv('Ski Classics - 2022-12-11 (7 - 18h) - Visitors During the Day.csv')

# Process each dataset to extract useful summary information
# 1. Summary of Arriving and Departing Visitors
arriving_summary = arriving_departing_df['Arriving visitors'].sum()
departing_summary = arriving_departing_df['Departing visitors'].sum()

# 2. Visitor Demographics
origin_summary = origin_df[['Area of origin', 'Visitors']].groupby('Area of origin').sum()
nationality_summary = top_countries_df

# 3. Stay Duration Insights
stay_duration_avg = stay_duration_df['Stay duration (minutes)'].mean()
stay_duration_min = stay_duration_df['Stay duration (minutes)'].min()
stay_duration_max = stay_duration_df['Stay duration (minutes)'].max()

# 4. Visitor Trends Throughout the Day
peak_time = visitors_day_df.loc[visitors_day_df['Visitors'].idxmax()]

# Consolidate into a final DataFrame
summary_data = {
    'Total Arriving Visitors': [arriving_summary],
    'Total Departing Visitors': [departing_summary],
    'Average Stay Duration (minutes)': [stay_duration_avg],
    'Min Stay Duration (minutes)': [stay_duration_min],
    'Max Stay Duration (minutes)': [stay_duration_max],
    'Peak Visitor Time': [peak_time['Time']]
}

final_summary_df = pd.DataFrame(summary_data)

# Save to a new CSV file
final_summary_df.to_csv('final_event_summary.csv', index=False)
